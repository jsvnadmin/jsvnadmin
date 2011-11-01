/**
 * 
 */
package org.svnadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;
import org.svnadmin.entity.LangLbl;

/**
 * 语言DAO
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 */
@Repository(LangLblDao.BEAN_NAME)
public class LangLblDao extends Dao {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME="langLblDao";

	/**
	 * 
	 * @param lang 
	 * @param id 
	 * @return
	 */
	public LangLbl get(String lang,String id) {
		String sql = "select lang,id,lbl from lang_lbl where lang=? and id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, lang);
			pstmt.setString(index++, id);

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
	 * 从ResultSet中读取readLangLbl对象
	 * 
	 * @param rs
	 *            ResultSet
	 * @return readLangLbl对象
	 * @throws SQLException
	 *             JDBC异常
	 */
	LangLbl readLangLbl(ResultSet rs) throws SQLException {
		LangLbl result = new LangLbl();
		result.setLang(rs.getString("lang"));
		result.setId(rs.getString("id"));
		result.setLbl(rs.getString("lbl"));
		return result;
	}
	/**
	 * 删除
	 * @param lang 
	 * 
	 */
	public void delete(String lang) {
		String sql = "delete from lang_lbl where lang=?";
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
	 * 删除
	 * @param lang 
	 * @param id 
	 * 
	 */
	public void delete(String lang,String id) {
		String sql = "delete from lang_lbl where lang=? and id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, lang);
			pstmt.setString(index++, id);
			
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
	 * @param langLbl
	 * 
	 * @return 更新数量
	 */
	public int update(LangLbl langLbl) {
		String sql = "update lang_lbl set lbl=? where lang=? and id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, langLbl.getLbl());
			pstmt.setString(index++, langLbl.getLang());
			pstmt.setString(index++, langLbl.getId());

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
	 * @param langLbl
	 * 
	 * @return 更新数量
	 */
	public int insert(LangLbl langLbl) {
		String sql = "insert into lang_lbl (lang,id,lbl) values (?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, langLbl.getLang());
			pstmt.setString(index++, langLbl.getId());
			pstmt.setString(index++, langLbl.getLbl());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

}
