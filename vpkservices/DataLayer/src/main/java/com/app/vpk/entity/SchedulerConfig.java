package com.app.vpk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scheduler_config")
public class SchedulerConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "task_name")
	private String taskName;

	@Column(name = "fixed_delay")
	private Long fixedDelay;

	// Getters and Setters
	public Long getFixedDelay() {
		return fixedDelay;
	}

	public void setFixedDelay(Long fixedDelay) {
		this.fixedDelay = fixedDelay;
	}
}


//CREATE TABLE scheduler_config (
//	    id INT PRIMARY KEY AUTO_INCREMENT,
//	    task_name VARCHAR(100) NOT NULL,
//	    fixed_delay BIGINT NOT NULL DEFAULT 5000  -- Default delay (in milliseconds)
//	);