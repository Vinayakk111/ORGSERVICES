package com.app.vpk.dto;

public class CurrencyResponse {

	private String code;
	private String name;
	private String symbol;
	private Integer minorUnit;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	@Override
	public String toString() {
		return "CurrencyResponse [code=" + code + ", name=" + name + ", symbol=" + symbol + ", minorUnit=" + minorUnit
				+ "]";
	}

	public CurrencyResponse(String code, String name, String symbol, Integer minorUnit) {
		super();
		this.code = code;
		this.name = name;
		this.symbol = symbol;
		this.minorUnit = minorUnit;
	}

	public CurrencyResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

}