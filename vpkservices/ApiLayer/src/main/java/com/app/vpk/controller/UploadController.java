package com.app.vpk.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.app.vpk.entity.ExcelProcessingBatch;
import com.app.vpk.entity.ExcelRowError;
import com.app.vpk.repository.ExcelProcessingBatchRepository;
import com.app.vpk.repository.ExcelRowErrorRepository;
import com.app.vpk.service.ExcelProcessingService;

@RestController
@RequestMapping("/api/excel")
public class UploadController {

	@Autowired
	private ExcelProcessingService excelProcessingService;
	@Autowired
	private ExcelProcessingBatchRepository batchRepository;
	@Autowired
	private ExcelRowErrorRepository rowErrorRepository;

	/**
	 * Example: curl -F "file=@employees.xlsx" \
	 * "http://localhost:8080/api/excel/upload?tableName=employee_data&ruleOutputColumns=risk_category,bonus_flag"
	 */
	@PostMapping(value = "/upload", consumes = "multipart/form-data")
	public ResponseEntity<ExcelProcessingBatch> upload(@RequestParam("file") MultipartFile file,
			@RequestParam("tableName") String tableName,
			@RequestParam(value = "ruleOutputColumns", required = false) List<String> ruleOutputColumns)
			throws IOException {

		ExcelProcessingBatch batch = excelProcessingService.processExcelFile(tableName, file, ruleOutputColumns);
		return ResponseEntity.ok(batch);
	}

	@GetMapping("/batches")
	public ResponseEntity<List<ExcelProcessingBatch>> listBatches() {
		List<ExcelProcessingBatch> batches = batchRepository.findAll();
		batches.sort((a, b) -> b.getStartedAt().compareTo(a.getStartedAt()));
		return ResponseEntity.ok(batches);
	}

	/** Summary (counts/status) for one batch. */
	@GetMapping("/batches/{id}")
	public ResponseEntity<ExcelProcessingBatch> getBatch(@PathVariable Long id) {
		return batchRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * The failed rows for one batch: row number, the row's own data, and why it
	 * failed.
	 */
	@GetMapping("/batches/{id}/errors")
	public ResponseEntity<List<ExcelRowError>> getBatchErrors(@PathVariable Long id) {
		return ResponseEntity.ok(rowErrorRepository.findByBatchId(id));
	}
}
