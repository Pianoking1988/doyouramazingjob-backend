package de.heinemann.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class SequenceResetter {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void resetSequences() {
		//List<String> tables = jdbcTemplate.queryForList("", String.class);
		String sql = "SELECT * FROM INFORMATION_SCHEMA.SYSTEM_TABLES where TABLE_TYPE='TABLE'";
		List<String> tables = jdbcTemplate.query(sql,
				new RowMapper<String>() {

					@Override
					public String mapRow(ResultSet resultSet, int arg1) throws SQLException {
						System.out.println("Row " + arg1);
						for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
							System.out.println(i + resultSet.getMetaData().getColumnName(i) +  " " + resultSet.getString(i));
						}
						return resultSet.getString(1);
					}
			
		});
		
		
		// SELECT table_name FROM INFORMATION_SCHEMA.SYSTEM_TABLES where TABLE_TYPE='TABLE'
		// SELECT table_name, column_name, identity_start FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'PUBLIC' AND IS_IDENTITY = 'YES'
		
		
		jdbcTemplate.execute("DELETE FROM users");
		jdbcTemplate.execute("ALTER TABLE users ALTER COLUMN id RESTART WITH 1");		
	}
	
}
