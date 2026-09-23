package com.app.vpk.entity;

/**
 * The kinds of business validation the DB-driven validation engine understands.
 * Add rows to `validation_rule` using these type names - no code change needed
 * for any of the built-in kinds. CUSTOM_EXPR is the escape hatch for anything
 * not covered by the named types: a JS boolean expression evaluated against the
 * row's own columns.
 */
public enum ValidationType {
	REQUIRED, // value must be non-null / non-blank
	NUMERIC, // value must parse as a decimal number
	INTEGER, // value must parse as a whole number
	MIN_LENGTH, // ruleParam = minimum string length
	MAX_LENGTH, // ruleParam = maximum string length
	REGEX, // ruleParam = regex the value must match
	RANGE, // ruleParam = "min,max" - numeric value must fall within (inclusive)
	ALLOWED_VALUES, // ruleParam = comma-separated allowed values (case-insensitive)
	DATE_FORMAT, // ruleParam = date pattern, e.g. yyyy-MM-dd
	CUSTOM_EXPR // ruleParam = JS boolean expression, row columns bound by name
}