package com.app.vpk.service;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.vpk.entity.ValidationRule;
import com.app.vpk.entity.ValidationType;
import com.app.vpk.repository.ValidationRuleRepository;

/**
 * Runs business validations that live entirely in the database (validation_rule
 * table) against a row of Excel data, e.g. "required", "numeric", "allowed
 * values", etc. Like RuleEngineService, this means validation logic is data,
 * not code - add/edit/ disable a validation by changing rows in
 * validation_rule, no redeploy required.
 */
@Service
public class ValidationEngineService {

	@Autowired
	private ValidationRuleRepository validationRuleRepository;

	private static final Logger log = LogManager.getLogger(ValidationEngineService.class);

	private final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");

	/**
	 * @return list of human-readable validation failure messages for this row;
	 *         empty if the row passes every active rule configured for tableName.
	 */
	public List<String> validate(String tableName, Map<String, Object> rowData) {
		List<ValidationRule> rules = validationRuleRepository
				.findByTargetTableAndActiveTrueOrderByPriorityAsc(tableName);

		List<String> errors = new ArrayList<>();

		for (ValidationRule rule : rules) {
			Object rawValue = rowData.get(rule.getColumnName());
			String value = rawValue == null ? null : String.valueOf(rawValue).trim();

			boolean valid = evaluate(rule, value, rowData);
			if (!valid) {
				errors.add(buildMessage(rule, value));
			}
		}

		return errors;
	}

	private boolean evaluate(ValidationRule rule, String value, Map<String, Object> rowData) {
		ValidationType type = rule.getValidationType();
		String param = rule.getRuleParam();

		// Only REQUIRED fails on a blank value; every other rule is considered
		// satisfied
		// when the field is empty (pair it with a REQUIRED rule if it must also be
		// present).
		if (type != ValidationType.REQUIRED && (value == null || value.isEmpty())) {
			return true;
		}

		try {
			switch (type) {
			case REQUIRED:
				return value != null && !value.isEmpty();

			case NUMERIC:
				Double.parseDouble(value);
				return true;

			case INTEGER:
				Long.parseLong(value);
				return true;

			case MIN_LENGTH:
				return value.length() >= Integer.parseInt(param.trim());

			case MAX_LENGTH:
				return value.length() <= Integer.parseInt(param.trim());

			case REGEX:
				return value.matches(param);

			case RANGE: {
				String[] bounds = param.split(",");
				double min = Double.parseDouble(bounds[0].trim());
				double max = Double.parseDouble(bounds[1].trim());
				double num = Double.parseDouble(value);
				return num >= min && num <= max;
			}

			case ALLOWED_VALUES: {
				List<String> allowed = Arrays.asList(param.split(","));
				return allowed.stream().anyMatch(a -> a.trim().equalsIgnoreCase(value));
			}

			case DATE_FORMAT:
				DateTimeFormatter.ofPattern(param).parse(value);
				return true;

			case CUSTOM_EXPR:
				return evaluateCustomExpr(param, rowData);

			default:
				log.warn("Unknown validation type {} on rule id={}", type, rule.getId());
				return true;
			}
		} catch (NumberFormatException | DateTimeParseException | ArrayIndexOutOfBoundsException e) {
			// The value itself failed to parse against what the rule expects - that's a
			// failure, not a bug.
			return false;
		} catch (Exception e) {
			log.warn("Validation rule id={} threw while evaluating: {}", rule.getId(), e.getMessage());
			return false;
		}
	}

	private boolean evaluateCustomExpr(String expr, Map<String, Object> rowData) throws javax.script.ScriptException {
		if (scriptEngine == null || expr == null) {
			return true;
		}
		Bindings bindings = scriptEngine.createBindings();
		bindings.putAll(rowData);
		Object result = scriptEngine.eval(expr, bindings);
		return Boolean.TRUE.equals(result) || "true".equalsIgnoreCase(String.valueOf(result));
	}

	private String buildMessage(ValidationRule rule, String value) {
		if (rule.getErrorMessage() != null && !rule.getErrorMessage().trim().isEmpty()) {
			return rule.getErrorMessage();
		}

		switch (rule.getValidationType()) {
		case REQUIRED:
			return rule.getColumnName() + " is required";
		case NUMERIC:
			return rule.getColumnName() + " must be a number (got '" + value + "')";
		case INTEGER:
			return rule.getColumnName() + " must be a whole number (got '" + value + "')";
		case MIN_LENGTH:
			return rule.getColumnName() + " must be at least " + rule.getRuleParam() + " characters";
		case MAX_LENGTH:
			return rule.getColumnName() + " must be at most " + rule.getRuleParam() + " characters";
		case REGEX:
			return rule.getColumnName() + " has an invalid format";
		case RANGE:
			return rule.getColumnName() + " must be between " + rule.getRuleParam();
		case ALLOWED_VALUES:
			return rule.getColumnName() + " must be one of: " + rule.getRuleParam();
		case DATE_FORMAT:
			return rule.getColumnName() + " must match date format " + rule.getRuleParam();
		case CUSTOM_EXPR:
		default:
			return rule.getColumnName() + " failed validation rule id=" + rule.getId();
		}
	}
}
