package com.app.vpk.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "currency", uniqueConstraints = { @UniqueConstraint(name = "uk_currency_code", columnNames = "code") })
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 3)
	private String code;

	@Column(name = "numeric_code", length = 3)
	private String numericCode;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(length = 10)
	private String symbol;

	@Column(name = "minor_unit", columnDefinition = "TINYINT" ,nullable = false)
	private Integer minorUnit = 2;

	@Column(nullable = false)
	private Boolean active = true;
	
	@Column(name = "created_at")
	private Instant createdAt;
	
	@Column(name = "updated_at")
	private Instant updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNumericCode() {
		return numericCode;
	}

	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getMinorUnit() {
		return minorUnit;
	}

	public void setMinorUnit(Integer minorUnit) {
		this.minorUnit = minorUnit;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Currency() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Currency(Long id, String code, String numericCode, String name, String symbol, Integer minorUnit,
			Boolean active, Instant createdAt, Instant updatedAt) {
		super();
		this.id = id;
		this.code = code;
		this.numericCode = numericCode;
		this.name = name;
		this.symbol = symbol;
		this.minorUnit = minorUnit;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Currency [id=" + id + ", code=" + code + ", numericCode=" + numericCode + ", name=" + name + ", symbol="
				+ symbol + ", minorUnit=" + minorUnit + ", active=" + active + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

}
