package com.app.vpk.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.vpk.entity.ValidationRule;

public interface ValidationRuleRepository extends JpaRepository<ValidationRule, Long> {

	List<ValidationRule> findByTargetTableAndActiveTrueOrderByPriorityAsc(String targetTable);
}