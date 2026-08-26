package com.app.vpk.entity;

import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "exchange_rate")
public class ExchangeRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "base_currency_id", nullable = false)
	private Currency baseCurrency;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "quote_currency_id", nullable = false)
	private Currency quoteCurrency;

	@Column(precision = 30, scale = 12, nullable = false)
	private BigDecimal rate;

	@Column(name = "inverse_rate", precision = 30, scale = 12)
	private BigDecimal inverseRate;

	@Column(nullable = false)
	private Instant rateDate;

	@Column(nullable = false)
	private String provider;

	private Instant sourceUpdatedAt;

	private Instant createdAt;

	private Instant updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Currency getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(Currency baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public Currency getQuoteCurrency() {
		return quoteCurrency;
	}

	public void setQuoteCurrency(Currency quoteCurrency) {
		this.quoteCurrency = quoteCurrency;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getInverseRate() {
		return inverseRate;
	}

	public void setInverseRate(BigDecimal inverseRate) {
		this.inverseRate = inverseRate;
	}

	public Instant getRateDate() {
		return rateDate;
	}

	public void setRateDate(Instant rateDate) {
		this.rateDate = rateDate;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Instant getSourceUpdatedAt() {
		return sourceUpdatedAt;
	}

	public void setSourceUpdatedAt(Instant sourceUpdatedAt) {
		this.sourceUpdatedAt = sourceUpdatedAt;
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

	public ExchangeRate() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ExchangeRate [id=" + id + ", baseCurrency=" + baseCurrency + ", quoteCurrency=" + quoteCurrency
				+ ", rate=" + rate + ", inverseRate=" + inverseRate + ", rateDate=" + rateDate + ", provider="
				+ provider + ", sourceUpdatedAt=" + sourceUpdatedAt + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}

	public ExchangeRate(Long id, Currency baseCurrency, Currency quoteCurrency, BigDecimal rate, BigDecimal inverseRate,
			Instant rateDate, String provider, Instant sourceUpdatedAt, Instant createdAt, Instant updatedAt) {
		super();
		this.id = id;
		this.baseCurrency = baseCurrency;
		this.quoteCurrency = quoteCurrency;
		this.rate = rate;
		this.inverseRate = inverseRate;
		this.rateDate = rateDate;
		this.provider = provider;
		this.sourceUpdatedAt = sourceUpdatedAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
