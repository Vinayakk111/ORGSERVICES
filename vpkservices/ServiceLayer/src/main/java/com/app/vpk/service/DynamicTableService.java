package com.app.vpk.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Creates the target table if it does not exist, and adds any missing columns
 * on the fly, based purely on the DB's metadata - no manual DDL needed.
 *
 * Uses JPA's EntityManager (Hibernate) for everything, including the DDL and
 * the per-row insert, instead of JdbcTemplate. Because the table/column set is
 * only known at runtime (driven by the Excel headers), there's no
 * static @Entity to map rows to, so this still issues native SQL - but through
 * EntityManager.createNativeQuery(...) and EntityManager-managed connections
 * rather than a JdbcTemplate bean.
 */
@Service
@RequiredArgsConstructor
public class DynamicTableService {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Ensures {@code tableName} exists and contains every column in
	 * {@code columnNames} (plus any extra rule-output columns you pass in). Missing
	 * pieces are created; existing ones are left untouched.
	 */
	@Transactional
	public void ensureTableAndColumns(String tableName, List<String> columnNames) {
		Session session = entityManager.unwrap(Session.class);

		// Use doReturningWork to inspect DatabaseMetaData through the same JPA-managed
		// connection.
		Boolean exists = session.doReturningWork(connection -> tableExists(connection, tableName));

		if (!Boolean.TRUE.equals(exists)) {
			createTable(tableName, columnNames);
		} else {
			Set<String> existingColumns = session
					.doReturningWork(connection -> getExistingColumns(connection, tableName));

			for (String column : columnNames) {
				if (!existingColumns.contains(column.toLowerCase())) {
					addColumn(tableName, column);
				}
			}
		}
	}

	private boolean tableExists(Connection connection, String tableName) throws SQLException {
		DatabaseMetaData metaData = connection.getMetaData();
		try (ResultSet rs = metaData.getTables(null, null, tableName, new String[] { "TABLE" })) {
			if (rs.next()) {
				return true;
			}
		}
		// some DBs report table names upper-cased
		try (ResultSet rs = metaData.getTables(null, null, tableName.toUpperCase(), new String[] { "TABLE" })) {
			return rs.next();
		}
	}

	private Set<String> getExistingColumns(Connection connection, String tableName) throws SQLException {
		DatabaseMetaData metaData = connection.getMetaData();
		Set<String> columns = new LinkedHashSet<>();

		try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
			while (rs.next()) {
				columns.add(rs.getString("COLUMN_NAME").toLowerCase());
			}
		}
		if (columns.isEmpty()) {
			try (ResultSet rs = metaData.getColumns(null, null, tableName.toUpperCase(), null)) {
				while (rs.next()) {
					columns.add(rs.getString("COLUMN_NAME").toLowerCase());
				}
			}
		}
		return columns;
	}

	private void createTable(String tableName, List<String> columnNames) {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE ").append(tableName).append(" (id BIGINT AUTO_INCREMENT PRIMARY KEY");

		for (String column : columnNames) {
			sql.append(", ").append(column).append(" VARCHAR(1000)");
		}
		sql.append(")");

		entityManager.createNativeQuery(sql.toString()).executeUpdate();
	}

	private void addColumn(String tableName, String columnName) {
		String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " VARCHAR(1000)";
		entityManager.createNativeQuery(sql).executeUpdate();
	}

	/**
	 * Parameterised insert built from the row's own keys - no need to know the
	 * column list in advance. Still native SQL (there's no static entity for a
	 * dynamic table), but executed via EntityManager rather than JdbcTemplate.
	 */
	@Transactional
	public void insertRow(String tableName, Map<String, Object> rowData) {
		if (rowData.isEmpty()) {
			return;
		}

		StringBuilder columns = new StringBuilder();
		StringBuilder placeholders = new StringBuilder();

		int i = 0;
		for (String column : rowData.keySet()) {
			if (i > 0) {
				columns.append(", ");
				placeholders.append(", ");
			}
			columns.append(column);
			placeholders.append(":p").append(i);
			i++;
		}

		String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
		Query query = entityManager.createNativeQuery(sql);

		i = 0;
		for (Object value : rowData.values()) {
			query.setParameter("p" + i, value);
			i++;
		}

		query.executeUpdate();
	}
}
