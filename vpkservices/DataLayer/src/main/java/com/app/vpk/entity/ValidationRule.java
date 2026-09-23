package com.app.vpk.entity;

import javax.persistence.*;

/**
 * A single business validation stored in the DB instead of hard-coded in Java.
 *
 * Examples: targetTable=employee_data, columnName=employee_name,
 * validationType=REQUIRED targetTable=employee_data, columnName=salary,
 * validationType=NUMERIC targetTable=employee_data, columnName=salary,
 * validationType=RANGE, ruleParam="0,10000000" targetTable=employee_data,
 * columnName=department, validationType=ALLOWED_VALUES,
 * ruleParam="SALES,MARKETING,SUPPORT,ENGINEERING" targetTable=employee_data,
 * columnName=joining_date, validationType=DATE_FORMAT, ruleParam="yyyy-MM-dd"
 * targetTable=employee_data, columnName=employee_name,
 * validationType=CUSTOM_EXPR, ruleParam="employee_name.length() <= 100"
 *
 * Multiple rules can target the same column (e.g. REQUIRED + NUMERIC + RANGE on
 * salary); all active rules for a table run for every row, and every failure is
 * collected.
 */
@Entity
@Table(name = "validation_rule")
public class ValidationRule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "target_table", nullable = false, length = 128)
	private String targetTable;

	@Column(name = "column_name", nullable = false, length = 128)
	private String columnName;

	@Enumerated(EnumType.STRING)
	@Column(name = "validation_type", nullable = false, length = 30)
	private ValidationType validationType;

	/**
	 * Extra parameter the validation type needs (pattern, min/max, allowed values,
	 * ...). Not needed for REQUIRED/NUMERIC/INTEGER.
	 */
	@Column(name = "rule_param", length = 2000)
	private String ruleParam;

	/**
	 * Shown to the user when this rule fails; a sensible default is used if left
	 * blank.
	 */
	@Column(name = "error_message", length = 500)
	private String errorMessage;

	@Column(name = "priority", nullable = false)
	private Integer priority = 100;

	@Column(name = "active", nullable = false)
	private Boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTargetTable() {
		return targetTable;
	}

	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public ValidationType getValidationType() {
		return validationType;
	}

	public void setValidationType(ValidationType validationType) {
		this.validationType = validationType;
	}

	public String getRuleParam() {
		return ruleParam;
	}

	public void setRuleParam(String ruleParam) {
		this.ruleParam = ruleParam;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}