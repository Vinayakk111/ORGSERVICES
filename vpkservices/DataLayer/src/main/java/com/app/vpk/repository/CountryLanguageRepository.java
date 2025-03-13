package com.app.vpk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.vpk.entity.CountryLanguage;


@Repository
public interface CountryLanguageRepository extends JpaRepository<CountryLanguage, Long>{

}
