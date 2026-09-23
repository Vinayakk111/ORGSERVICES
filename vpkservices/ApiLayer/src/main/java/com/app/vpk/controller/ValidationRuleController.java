package com.app.vpk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.vpk.entity.ValidationRule;
import com.app.vpk.repository.ValidationRuleRepository;

import lombok.RequiredArgsConstructor;

/**
 * Minimal CRUD for managing validation_rule rows over REST instead of
 * hand-writing SQL - this is how the "rule engine" gets configured at runtime.
 */
@RestController
@RequestMapping("/api/validation-rules")
@RequiredArgsConstructor
public class ValidationRuleController {

	@Autowired
	private ValidationRuleRepository validationRuleRepository;

	@GetMapping
	public List<ValidationRule> list(@RequestParam(required = false) String targetTable) {
		if (targetTable != null) {
			return validationRuleRepository.findByTargetTableAndActiveTrueOrderByPriorityAsc(targetTable);
		}
		return validationRuleRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<ValidationRule> create(@RequestBody ValidationRule rule) {
		rule.setId(null);
		return ResponseEntity.ok(validationRuleRepository.save(rule));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ValidationRule> update(@PathVariable Long id, @RequestBody ValidationRule updated) {
		return validationRuleRepository.findById(id).map(existing -> {
			existing.setTargetTable(updated.getTargetTable());
			existing.setColumnName(updated.getColumnName());
			existing.setValidationType(updated.getValidationType());
			existing.setRuleParam(updated.getRuleParam());
			existing.setErrorMessage(updated.getErrorMessage());
			existing.setPriority(updated.getPriority());
			existing.setActive(updated.getActive());
			return ResponseEntity.ok(validationRuleRepository.save(existing));
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!validationRuleRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		validationRuleRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
