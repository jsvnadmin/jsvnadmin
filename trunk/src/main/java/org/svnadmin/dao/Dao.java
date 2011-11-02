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

/**
 * 所有DAO的父类
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 * 
 */
public class Dao {

	/**
	 * 日志
	 */
	private final Log LOG = LogFactory.getLog(Dao.class);

	/**
	 * 数据源
	 */
	@Resource(name = "dataSource")
	DataSource dataSource;

	/**
	 * @return 数据源
	 */
	protected DataSource getDataSource() {
		return this.dataSource;
	}

	/**
	 * @return 数据库连接
	 */
	protected Connection getConnection() {
		return DataSourceUtils.getConnection(getDataSource());
	}
	/**
	 * 验证是否可以连接上数据库 see Issue 12
	 * 
	 * @throws SQLException jdbc异常
	 */
	public void validatConnection() throws SQLException {
		Connection conn = null;
		try {
			conn = this.getConnection();
		} finally {
			this.close(null, null, conn);
		}
	}
	/**
	 * 关闭资源
	 * 
	 * @param rs
	 *            ResultSet
	 * @param s
	 *            Statement
	 * @param conn
	 *            数据库连接
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