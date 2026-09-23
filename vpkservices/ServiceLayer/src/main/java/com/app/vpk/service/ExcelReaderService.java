package com.app.vpk.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads an uploaded Excel file (.xls / .xlsx) using Apache POI.
 * Row 0 is treated as the header row and becomes the column names.
 */
@Service
public class ExcelReaderService {

    /**
     * @return list of rows, each row represented as an ordered Map<columnName, cellValueAsString>
     */
    public List<Map<String, Object>> readExcel(MultipartFile file) throws IOException {
        List<Map<String, Object>> rows = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            Row headerRow = sheet.getRow(sheet.getFirstRowNum());
            if (headerRow == null) {
                return rows;
            }

            List<String> columnNames = new ArrayList<>();
            for (Cell cell : headerRow) {
                columnNames.add(sanitizeColumnName(formatter.formatCellValue(cell)));
            }

            for (int r = sheet.getFirstRowNum() + 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                Map<String, Object> rowData = new LinkedHashMap<>();
                boolean rowHasData = false;

                for (int c = 0; c < columnNames.size(); c++) {
                    Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    String value = cell == null ? null : formatter.formatCellValue(cell);
                    if (value != null && !value.trim().isEmpty()) {
                        rowHasData = true;
                    }
                    rowData.put(columnNames.get(c), value);
                }

                if (rowHasData) {
                    rows.add(rowData);
                }
            }
        }

        return rows;
    }

    /**
     * Converts an Excel header like "Employee Name" -> "employee_name" so it is a safe,
     * predictable SQL column identifier.
     */
    private String sanitizeColumnName(String header) {
        if (header == null) {
            return "column";
        }
        String cleaned = header.trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9_]+", "_")
                .replaceAll("_+", "_")
                .replaceAll("^_|_$", "");
        if (cleaned.isEmpty()) {
            cleaned = "column";
        }
        if (Character.isDigit(cleaned.charAt(0))) {
            cleaned = "c_" + cleaned;
        }
        return cleaned;
    }
}
