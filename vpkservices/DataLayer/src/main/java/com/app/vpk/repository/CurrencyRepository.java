package com.app.vpk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.vpk.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

	Optional<Currency> findByCode(String code);

	boolean existsByCode(String code);
}