package com.app.vpk.services;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.vpk.dto.FloatRateDto;
import com.app.vpk.dto.FloatRatesResponse;
import com.app.vpk.entity.Currency;
import com.app.vpk.entity.ExchangeRate;
import com.app.vpk.repository.CurrencyRepository;
import com.app.vpk.repository.ExchangeRateRepository;

@Service
public class FloatRatesSyncService {

	private static final String PROVIDER = "FLOATRATES";

	private static final String BASE = "USD";

	@Autowired
	private FloatRatesClient client;

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private ExchangeRateRepository rateRepository;

	public List<ExchangeRate> getCurrency(String from,String to) {
		return rateRepository.findLatestRate(from,to,PageRequest.of(0, 1));
	}

	@Transactional
	public void sync() {
		try {
			System.out.println("FloatRates synchronization Started");
			FloatRatesResponse response = client.getUsdRates();

			Currency usd = currencyRepository.findByCode(BASE)
					.orElseThrow(() -> new IllegalStateException("USD currency missing"));

			for (FloatRateDto dto : response.values()) {
				System.out.println("FloatRates synchronization completed" + usd);
				processRate(usd, dto);
			}

			System.out.println("FloatRates synchronization completed");
//	        log.info(
//	                "FloatRates synchronization completed. " +
//	                "Currencies={}",
//	                response.size()
//	        );	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processRate(Currency usd, FloatRateDto dto) {

		String code = dto.getAlphaCode().toUpperCase();

		Currency currency = currencyRepository.findByCode(code).orElseGet(() -> createCurrency(dto));

		Instant sourceDate = parseDate(dto.getDate());

		ExchangeRate rate = rateRepository
				.findByBaseCurrency_CodeAndQuoteCurrency_CodeAndRateDateAndProvider("USD", code, sourceDate, PROVIDER)
				.orElseGet(ExchangeRate::new);

		rate.setBaseCurrency(usd);
		rate.setQuoteCurrency(currency);
		rate.setRate(dto.getRate());
		rate.setInverseRate(dto.getInverseRate());
		rate.setRateDate(sourceDate);
		rate.setProvider(PROVIDER);
		rate.setSourceUpdatedAt(sourceDate);
		rate.setUpdatedAt(Instant.now());

		if (rate.getCreatedAt() == null) {
			rate.setCreatedAt(Instant.now());
		}

		rateRepository.save(rate);
	}

	private Currency createCurrency(FloatRateDto dto) {

		Currency currency = new Currency();

		currency.setCode(dto.getAlphaCode());

		currency.setNumericCode(dto.getNumericCode());

		currency.setName(dto.getName());

		currency.setActive(true);

		currency.setMinorUnit(2);

		currency.setCreatedAt(Instant.now());

		currency.setUpdatedAt(Instant.now());

		return currencyRepository.save(currency);
	}

	private Instant parseDate(String value) {

		return ZonedDateTime.parse(value, DateTimeFormatter.RFC_1123_DATE_TIME).toInstant();
	}
}