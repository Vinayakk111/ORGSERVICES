package com.app.vpk.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * One row per failed Excel row within a given batch - the row's own data plus
 * why it failed, so a user/admin can look up exactly what went wrong and
 * re-submit a corrected file.
 */
@Entity
@Table(name = "excel_row_error")
public class ExcelRowError {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Not serialized in API responses: the batch id is already known from the URL
	// (/batches/{id}/errors), and serializing a LAZY association outside an open
	// session/transaction would throw (open-in-view is disabled).
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batch_id", nullable = false)
	private ExcelProcessingBatch batch;

	/**
	 * 1-based position of this row within the data rows of the sheet (header
	 * excluded).
	 */
	@Column(name = "row_num")
	private Integer rowNumber;

	/**
	 * VALIDATION (failed a business rule, row never inserted) or INSERT_ERROR (DB
	 * rejected the row).
	 */
	@Column(name = "error_type", length = 20)
	private String errorType;

	/**
	 * The row's own column->value data, serialized as JSON, for troubleshooting.
	 */
	@Lob
	@Column(name = "row_data_json")
	private String rowDataJson;

	@Column(name = "error_message", length = 2000)
	private String errorMessage;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExcelProcessingBatch getBatch() {
		return batch;
	}

	public void setBatch(ExcelProcessingBatch batch) {
		this.batch = batch;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getRowDataJson() {
		return rowDataJson;
	}

	public void setRowDataJson(String rowDataJson) {
		this.rowDataJson = rowDataJson;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}