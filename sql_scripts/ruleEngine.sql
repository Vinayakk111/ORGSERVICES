-- world.excel_processing_batch definition

CREATE TABLE `excel_processing_batch` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(500) DEFAULT NULL,
  `target_table` varchar(128) NOT NULL,
  `total_rows` int DEFAULT '0',
  `success_count` int DEFAULT '0',
  `failure_count` int DEFAULT '0',
  `status` varchar(20) NOT NULL DEFAULT 'IN_PROGRESS',
  `error_message` varchar(2000) DEFAULT NULL,
  `started_at` datetime NOT NULL,
  `completed_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- world.excel_row_error definition

CREATE TABLE `excel_row_error` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `batch_id` bigint NOT NULL,
  `row_num` int DEFAULT NULL,
  `row_data_json` longtext,
  `error_message` varchar(2000) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `error_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_excel_row_error_batch` (`batch_id`),
  CONSTRAINT `fk_excel_row_error_batch` FOREIGN KEY (`batch_id`) REFERENCES `excel_processing_batch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- world.rule_definition definition

CREATE TABLE `rule_definition` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `target_table` varchar(128) NOT NULL,
  `target_column` varchar(128) NOT NULL,
  `condition_expr` varchar(2000) NOT NULL,
  `result_expr` varchar(2000) NOT NULL,
  `priority` int NOT NULL DEFAULT '100',
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO world.rule_definition (target_table,target_column,condition_expr,result_expr,priority,active) VALUES
	 ('employee_data','risk_category','parseFloat(salary) > 100000','''HIGH''',1,1),
	 ('employee_data','risk_category','parseFloat(salary) <= 100000 && parseFloat(salary) > 50000','''MEDIUM''',2,1),
	 ('employee_data','risk_category','parseFloat(salary) <= 50000','''LOW''',3,1),
	 ('employee_data','bonus_flag','department == ''SALES'' && parseFloat(salary) > 80000','''ELIGIBLE''',1,1);



-- world.validation_rule definition

CREATE TABLE `validation_rule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `target_table` varchar(128) NOT NULL,
  `column_name` varchar(128) NOT NULL,
  `validation_type` varchar(30) NOT NULL,
  `rule_param` varchar(2000) DEFAULT NULL,
  `error_message` varchar(500) DEFAULT NULL,
  `priority` int NOT NULL DEFAULT '100',
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO world.validation_rule (target_table,column_name,validation_type,rule_param,error_message,priority,active) VALUES
	 ('employee_data','employee_name','REQUIRED',NULL,'Employee name is required',1,1),
	 ('employee_data','salary','REQUIRED',NULL,'Salary is required',1,1),
	 ('employee_data','department','REQUIRED',NULL,'Department is required',1,1),
	 ('employee_data','salary','NUMERIC',NULL,'Salary must be a valid number',2,1),
	 ('employee_data','salary','RANGE','0,10000000','Salary must be between 0 and 10,000,000',3,1),
	 ('employee_data','joining_date','DATE_FORMAT','yyyy-MM-dd','Joining date must be in yyyy-MM-dd format',2,1),
	 ('employee_data','employee_name','MAX_LENGTH','100','Employee name must be 100 characters or fewer',2,1),
	 ('employee_data','department','ALLOWED_VALUES','SALES,MARKETING,SUPPORT,ENGINEERING','Department must be one of SALES, MARKETING, SUPPORT, ENGINEERING',2,1),
	 ('employee_data','employee_name','CUSTOM_EXPR','!/[0-9]/.test(employee_name)','Employee name must not contain digits',3,1);
