package com.app.vpk.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.vpk.entity.ExcelProcessingBatch;
import com.app.vpk.entity.ExcelRowError;
import com.app.vpk.repository.ExcelProcessingBatchRepository;
import com.app.vpk.repository.ExcelRowErrorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelProcessingService {

	@Autowired
	private ExcelReaderService excelReaderService;
	@Autowired
	private DynamicTableService dynamicTableService;
	@Autowired
	private RuleEngineService ruleEngineService;
	@Autowired
	private ExcelProcessingBatchRepository batchRepository;
	@Autowired
	private ExcelRowErrorRepository rowErrorRepository;
	@Autowired
	private ValidationEngineService validationEngineService;
	@Autowired
	private ObjectMapper objectMapper;

	private static final Logger log = LogManager.getLogger(ExcelProcessingService.class);

	/**
	 * Full pipeline: read Excel -> ensure table/columns exist -> apply DB-stored
	 * rules per row -> insert rows.
	 *
	 * @return number of rows inserted
	 */
	public ExcelProcessingBatch processExcelFile(String tableName, MultipartFile file, List<String> ruleOutputColumns) {
		ExcelProcessingBatch batch = new ExcelProcessingBatch();
		batch.setFileName(file.getOriginalFilename());
		batch.setTargetTable(tableName);
		batch.setStatus("IN_PROGRESS");
		batch = batchRepository.save(batch);

		List<Map<String, Object>> rows;
		try {
			rows = excelReaderService.readExcel(file);
		} catch (IOException e) {
			return failBatch(batch, "Failed to read Excel file: " + e.getMessage());
		}

		batch.setTotalRows(rows.size());

		if (rows.isEmpty()) {
			batch.setStatus("SUCCESS");
			batch.setCompletedAt(LocalDateTime.now());
			return batchRepository.save(batch);
		}

		Set<String> allColumns = new LinkedHashSet<>(rows.get(0).keySet());
		if (ruleOutputColumns != null) {
			allColumns.addAll(ruleOutputColumns);
		}

		try {
			dynamicTableService.ensureTableAndColumns(tableName, new ArrayList<>(allColumns));
		} catch (Exception e) {
			return failBatch(batch, "Failed to prepare table " + tableName + ": " + e.getMessage());
		}

		int success = 0;
		int failure = 0;

		for (int i = 0; i < rows.size(); i++) {
			Map<String, Object> row = rows.get(i);
			int rowNumber = i + 1; // 1-based, excluding the header row

			List<String> validationErrors = validationEngineService.validate(tableName, row);
			if (!validationErrors.isEmpty()) {
				failure++;
				String joined = String.join("; ", validationErrors);
				log.warn("Row {} failed validation for table {}: {}", rowNumber, tableName, joined);
				recordRowError(batch, rowNumber, row, "VALIDATION", joined);
				continue;
			}

			try {
				ruleEngineService.applyRules(tableName, row);
				dynamicTableService.insertRow(tableName, row);
				success++;
			} catch (Exception e) {
				failure++;
				log.warn("Row {} failed for table {}: {}", rowNumber, tableName, e.getMessage());
				recordRowError(batch, rowNumber, row, "INSERT_ERROR", e.getMessage());
			}
		}

		batch.setSuccessCount(success);
		batch.setFailureCount(failure);
		batch.setCompletedAt(LocalDateTime.now());
		batch.setStatus(failure == 0 ? "SUCCESS" : (success == 0 ? "FAILED" : "PARTIAL"));

		return batchRepository.save(batch);
	}

	private void recordRowError(ExcelProcessingBatch batch, int rowNumber, Map<String, Object> row, String errorType,
			String message) {
		try {
			ExcelRowError rowError = new ExcelRowError();
			rowError.setBatch(batch);
			rowError.setRowNumber(rowNumber);
			rowError.setErrorType(errorType);
			rowError.setErrorMessage(truncate(message, 2000));
			rowError.setRowDataJson(toJsonSafely(row));
			rowErrorRepository.save(rowError);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private ExcelProcessingBatch failBatch(ExcelProcessingBatch batch, String message) {
		batch.setStatus("FAILED");
		batch.setErrorMessage(truncate(message, 2000));
		batch.setCompletedAt(LocalDateTime.now());
		return batchRepository.save(batch);
	}

	private String toJsonSafely(Map<String, Object> row) {
		try {
			return objectMapper.writeValueAsString(row);
		} catch (Exception e) {
			return String.valueOf(row);
		}
	}

	private String truncate(String value, int maxLength) {
		if (value == null) {
			return null;
		}
		return value.length() > maxLength ? value.substring(0, maxLength) : value;
	}

}
