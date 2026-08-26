package com.app.vpk.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.vpk.entity.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

	Optional<ExchangeRate> findTopByBaseCurrency_CodeAndQuoteCurrency_CodeOrderByRateDateDesc(String base,
			String quote);

	Optional<ExchangeRate> findByBaseCurrency_CodeAndQuoteCurrency_CodeAndRateDateAndProvider(String base, String quote,
			Instant rateDate, String provider);
	
	@Query("SELECT er " +
		       "FROM ExchangeRate er, Currency fromCurrency, Currency toCurrency " +
		       "WHERE er.baseCurrency = fromCurrency.id " +
		       "AND er.quoteCurrency = toCurrency.id " +
		       "AND fromCurrency.code = :fromCode " +
		       "AND toCurrency.code = :toCode " +
		       "ORDER BY er.rateDate DESC")
		List<ExchangeRate> findLatestRate(
		        @Param("fromCode") String fromCode,
		        @Param("toCode") String toCode,
		        Pageable pageable);
}