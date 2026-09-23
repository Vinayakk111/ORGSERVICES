package com.app.vpk.repository;


import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.app.vpk.entity.RuleDefinition;

public interface RuleDefinitionRepository extends JpaRepository<RuleDefinition, Long> {

	List<RuleDefinition> findByTargetTableAndActiveTrueOrderByPriorityAsc(String targetTable);
	
	@Query(value = "select distinct target_column from rule_definition where target_table = :tableName and target_column NOT IN (:excludedColumns)", 
    nativeQuery = true)
	List<String> getColumnsExcept(@Param("tableName") String tableName, 
                           @Param("excludedColumns") Set<String> excludedColumns);
	
}