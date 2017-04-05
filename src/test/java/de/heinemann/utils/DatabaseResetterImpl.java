package de.heinemann.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * Clears all tables and restarts the HSQLDB identities used for auto-incremented columns.
 */
@Component
public class DatabaseResetterImpl implements DatabaseResetter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void resetDatabase() {
		clearTables();
		restartIdentities();		
	}
	
	private void clearTables() {
		final String sql = "SELECT table_name FROM information_schema.system_tables WHERE table_type = 'TABLE' AND table_schem = 'PUBLIC'";
			
		jdbcTemplate
				.queryForList(sql, String.class)
				.stream().forEach(table -> {
					logger.debug("Clear table {}", table);
					jdbcTemplate.execute(String.format("DELETE FROM \"%s\"", table));
				});
	}

	private void restartIdentities() {
		final String sql = "SELECT table_name, column_name, identity_start FROM information_schema.columns"
				+ " WHERE table_schema = 'PUBLIC' AND is_identity = 'YES'";

		jdbcTemplate.query(sql, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
				restartIdentity(resultSet.getString("table_name"),
						resultSet.getString("column_name"),
						resultSet.getLong("identity_start"));
				return null;
			}		
		});
	}
	
	private void restartIdentity(String tableName, String columnName, long identityStart) {
		logger.debug("Restart column {} in table {} with value {}", columnName, tableName, identityStart);

		String restartSql = String.format("ALTER TABLE %s ALTER COLUMN %s RESTART WITH %d", tableName, columnName, identityStart);
		jdbcTemplate.execute(restartSql);		
	}
	
}
