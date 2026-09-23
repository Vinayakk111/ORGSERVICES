package com.app.vpk.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * One row per Excel upload. Tracks how many rows were processed vs failed, so
 * results can be looked up later instead of only being returned in the original
 * HTTP response.
 */
@Entity
@Table(name = "excel_processing_batch")

public class ExcelProcessingBatch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "file_name", length = 500)
	private String fileName;

	@Column(name = "target_table", nullable = false, length = 128)
	private String targetTable;

	@Column(name = "total_rows")
	private Integer totalRows = 0;

	@Column(name = "success_count")
	private Integer successCount = 0;

	@Column(name = "failure_count")
	private Integer failureCount = 0;

	/** IN_PROGRESS, SUCCESS, PARTIAL, FAILED */
	@Column(name = "status", nullable = false, length = 20)
	private String status = "IN_PROGRESS";

	@Column(name = "error_message", length = 2000)
	private String errorMessage;

	@Column(name = "started_at", nullable = false)
	private LocalDateTime startedAt = LocalDateTime.now();

	@Column(name = "completed_at")
	private LocalDateTime completedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTargetTable() {
		return targetTable;
	}

	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}

	public LocalDateTime getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(LocalDateTime completedAt) {
		this.completedAt = completedAt;
	}

}
