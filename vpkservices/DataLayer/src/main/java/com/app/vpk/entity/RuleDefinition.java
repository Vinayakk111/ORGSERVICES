package com.app.vpk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A rule stored in the database instead of hard-coded in Java.
 *
 * Example row: targetTable = "employee_data" targetColumn = "risk_category"
 * conditionExpr = "salary > 100000" (JS boolean expression, evaluated against
 * the row's columns) resultExpr = "'HIGH'" (JS expression - its result is
 * written into targetColumn) priority = 1 (lower runs first; later matching
 * rules can overwrite earlier ones) active = true
 *
 * conditionExpr / resultExpr are plain JavaScript snippets evaluated at runtime
 * via the Nashorn engine bundled with Java 8 (javax.script), so rules can be
 * added/edited/disabled purely by inserting/updating rows in this table - no
 * redeploy needed.
 */
@Entity
@Table(name = "rule_definition")
public class RuleDefinition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "target_table", nullable = false, length = 128)
	private String targetTable;

	@Column(name = "target_column", nullable = false, length = 128)
	private String targetColumn;

	@Column(name = "condition_expr", nullable = false, length = 2000)
	private String conditionExpr;

	@Column(name = "result_expr", nullable = false, length = 2000)
	private String resultExpr;

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

	public String getTargetColumn() {
		return targetColumn;
	}

	public void setTargetColumn(String targetColumn) {
		this.targetColumn = targetColumn;
	}

	public String getConditionExpr() {
		return conditionExpr;
	}

	public void setConditionExpr(String conditionExpr) {
		this.conditionExpr = conditionExpr;
	}

	public String getResultExpr() {
		return resultExpr;
	}

	public void setResultExpr(String resultExpr) {
		this.resultExpr = resultExpr;
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
