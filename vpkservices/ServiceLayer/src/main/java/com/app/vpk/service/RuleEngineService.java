package com.app.vpk.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.vpk.entity.RuleDefinition;
import com.app.vpk.repository.RuleDefinitionRepository;

/**
 * Applies rules that live entirely in the database (rule_definition table) to a
 * row of Excel data - the "rule engine outside of the code".
 *
 * Rules are plain JS expressions (evaluated with the Nashorn engine that ships
 * with Java 8), so business users / admins can change behaviour by editing rows
 * in rule_definition, with no Java code change or redeploy required.
 */
@Service
public class RuleEngineService {

	@Autowired
	private RuleDefinitionRepository ruleDefinitionRepository;

	private final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");

	private static final Logger logger = LogManager.getLogger(RuleEngineService.class);

	/**
	 * Mutates and returns rowData: for every active rule configured for tableName
	 * whose conditionExpr evaluates to true against the row's own columns,
	 * rowData.get(targetColumn) is set to the evaluated resultExpr.
	 */
	public Map<String, Object> applyRules(String tableName, Map<String, Object> rowData) {
		List<RuleDefinition> rules = ruleDefinitionRepository
				.findByTargetTableAndActiveTrueOrderByPriorityAsc(tableName);

		if (rules.isEmpty() || scriptEngine == null) {
			return rowData;
		}

		for (RuleDefinition rule : rules) {
			try {
				Bindings bindings = scriptEngine.createBindings();
				bindings.putAll(rowData);

				Object conditionResult = scriptEngine.eval(rule.getConditionExpr(), bindings);
				boolean matches = Boolean.TRUE.equals(conditionResult)
						|| "true".equalsIgnoreCase(String.valueOf(conditionResult));

				if (matches) {
					Object resultValue = scriptEngine.eval(rule.getResultExpr(), bindings);
					rowData.put(rule.getTargetColumn(), resultValue == null ? null : String.valueOf(resultValue));
				}
			} catch (ScriptException e) {

				logger.error("Rule id={} failed to evaluate for table={}: {}", rule.getId(), tableName, e.getMessage());
			}
		}

		return rowData;
	}

	public Set<String> getTargetColumns(String tableName) {
		return ruleDefinitionRepository.findByTargetTableAndActiveTrueOrderByPriorityAsc(tableName).stream()
				.map(RuleDefinition::getTargetColumn).collect(Collectors.toCollection(LinkedHashSet::new));
	}
}
