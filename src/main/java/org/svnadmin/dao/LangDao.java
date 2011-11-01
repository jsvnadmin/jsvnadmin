/**
 * 
 */
package org.svnadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;
import org.svnadmin.entity.Lang;

/**
 * 语言DAO
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 */
@Repository(LangDao.BEAN_NAME)
public class LangDao extends Dao {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME="langDao";

	/**
	 * 
	 * @param lang 
	 * @return
	 */
	public Lang get(String lang) {
		String sql = "select lang,des from lang where lang=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, lang);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return readLangLbl(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
		return null;
	}
	/**
	 * 从ResultSet中读取lang对象
	 * 
	 * @param rs
	 *            ResultSet
	 * @return lang对象
	 * @throws SQLException
	 *             JDBC异常
	 */
	Lang readLangLbl(ResultSet rs) throws SQLException {
		Lang result = new Lang();
		result.setLang(rs.getString("lang"));
		result.setDes(rs.getString("des"));
		return result;
	}
	/**
	 * 删除
	 * @param lang 
	 * 
	 */
	public void delete(String lang) {
		String sql = "delete from lang where lang=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, lang);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	/**
	 * 更新
	 * 
	 * @param lang
	 * 
	 * @return 更新数量
	 */
	public int update(Lang lang) {
		String sql = "update lang set des=? where lang=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, lang.getDes());
			pstmt.setString(index++, lang.getLang());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	/**
	 * 增加
	 * 
	 * @param lang
	 * 
	 * @return 更新数量
	 */
	public int insert(Lang lang) {
		String sql = "insert into lang (lang,des) values (?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, lang.getLang());
			pstmt.setString(index++, lang.getDes());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}


}
