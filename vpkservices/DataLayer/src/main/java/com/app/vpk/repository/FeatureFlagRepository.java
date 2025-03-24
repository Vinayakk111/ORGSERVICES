package com.app.vpk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.vpk.entity.FeatureFlag;

public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, String> {

}
