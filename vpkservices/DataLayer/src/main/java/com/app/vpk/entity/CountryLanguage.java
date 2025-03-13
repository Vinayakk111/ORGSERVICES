package com.app.vpk.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "countrylanguage")
public class CountryLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="Language")
	private String Language;
	
	
	@Column(name="CountryCode")
	private String CountryCode;
	
	@Column(name = "IsOfficial", nullable = false)
    private String isOfficial; // 'T' or 'F'

    @Column(name = "Percentage", precision = 4, scale = 1, nullable = false)
    private BigDecimal percentage;

	public String getCountryCode() {
		return CountryCode;
	}

	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}

	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    // Constructors
    public CountryLanguage() {}

    public CountryLanguage( String isOfficial, BigDecimal percentage) {
        
        this.isOfficial = isOfficial;
        this.percentage = percentage;
    }

    public String getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(String isOfficial) {
        this.isOfficial = isOfficial;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    // toString method
    @Override
    public String toString() {
        return "CountryLanguage{" +
                "id=" +
                ", isOfficial='" + isOfficial + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}
