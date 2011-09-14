package org.svnadmin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class Dao {

	private final Log LOG = LogFactory.getLog(Dao.class);

	@Resource(name = "dataSource")
	DataSource dataSource;

	public Dao() {
	}

	protected DataSource getDataSource() {
		return this.dataSource;
	}

	protected Connection getConnection() {
		return DataSourceUtils.getConnection(getDataSource());
	}

	/**
	 * 关闭资源
	 * 
	 * @param rs
	 * @param pstmt
	 * @param cs
	 * @param conn
	 */
	protected void close(ResultSet rs, Statement s, Connection conn) {
		// rs
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
		// s
		if (s != null) {
			try {
				s.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
		// conn
		if (conn != null) {
			try {
				// conn.close();
				DataSourceUtils.releaseConnection(conn, getDataSource());
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}

	}
}