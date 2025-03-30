package com.app.vpk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FF4J_FEATURES")
public class FeatureFlag {

	@Id
	@Column(name = "FEAT_UID", length = 100, nullable = false, unique = true)
	private String featUid; // Unique feature name (Primary Key)

	@Column(name = "ENABLE", nullable = false)
	private Boolean enable; // true = enabled, false = disabled

	@Column(name = "DESCRIPTION", columnDefinition = "TEXT")
	private String description; // Description of the feature

	@Column(name = "STRATEGY", length = 255)
	private String strategy; // Custom strategy class for rollouts

	@Column(name = "EXPRESSION", columnDefinition = "TEXT")
	private String expression; // Parameters for the strategy

	@Column(name = "GROUPNAME", length = 100)
	private String groupName; // Group name for organizing features

	public String getFeatUid() {
		return featUid;
	}

	public void setFeatUid(String featUid) {
		this.featUid = featUid;
	}

	public Boolean isEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return "FeatureFlag [featUid=" + featUid + ", enable=" + enable + ", description=" + description + ", strategy="
				+ strategy + ", expression=" + expression + ", groupName=" + groupName + "]";
	}

	public FeatureFlag(String featUid, boolean enable, String description, String strategy, String expression,
			String groupName) {
		super();
		this.featUid = featUid;
		this.enable = enable;
		this.description = description;
		this.strategy = strategy;
		this.expression = expression;
		this.groupName = groupName;
	}

	public FeatureFlag() {
		super();
		// TODO Auto-generated constructor stub
	}

}

//-- world.ff4j_features definition
//
//CREATE TABLE `ff4j_features` (
//  `FEAT_UID` varchar(100) NOT NULL,
//  `ENABLE` tinyint(1) NOT NULL DEFAULT '0',
//  `DESCRIPTION` text,
//  `STRATEGY` varchar(255) DEFAULT NULL,
//  `EXPRESSION` text,
//  `GROUPNAME` varchar(100) DEFAULT NULL,
//  PRIMARY KEY (`FEAT_UID`)
//)