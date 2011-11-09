/**
 * 
 */
package org.svnadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.svnadmin.entity.I18n;

/**
 * 语言DAO
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 */
@Repository(I18nDao.BEAN_NAME)
public class I18nDao extends Dao {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME="i18nDao";

	/**
	 * 
	 * @param lang 语言
	 * @param id 键值
	 * @return 多语言
	 */
	public I18n get(String lang,String id) {
		String sql = "select lang,id,lbl from i18n where lang=? and id=?";
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
				return readI18n(rs);
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
	 * 
	 * @return 多语言列表
	 */
	public List<I18n> getList() {
		String sql = "select lang,id,lbl from i18n order by lang,id";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<I18n> results = new ArrayList<I18n>();
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				results.add(readI18n(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
		return results;
	}
	/**
	 * @param id 键值
	 * @return 相同键值的语言列表
	 */
	public Map<String,I18n> getI18ns(String id) {
		String sql = "select lang,id,lbl from i18n where id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String,I18n> results = new HashMap<String,I18n>();
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, id);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				I18n i18n = readI18n(rs);
				results.put(i18n.getLang(),i18n);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
		return results;
	}
	/**
	 * 从ResultSet中读取i18n对象
	 * 
	 * @param rs
	 *            ResultSet
	 * @return i18n对象
	 * @throws SQLException
	 *             JDBC异常
	 */
	I18n readI18n(ResultSet rs) throws SQLException {
		I18n result = new I18n();
		result.setLang(rs.getString("lang"));
		result.setId(rs.getString("id"));
		result.setLbl(rs.getString("lbl"));
		return result;
	}

	/**
	 * 更新
	 * 
	 * @param i18n 多语言
	 * 
	 * @return 更新数量
	 */
	public int update(I18n i18n) {
		String sql = "update i18n set lbl=? where lang=? and id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, i18n.getLbl());
			pstmt.setString(index++, i18n.getLang());
			pstmt.setString(index++, i18n.getId());

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
	 * @param i18n 多语言
	 * 
	 * @return 更新数量
	 */
	public int insert(I18n i18n) {
		String sql = "insert into i18n (lang,id,lbl) values (?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, i18n.getLang());
			pstmt.setString(index++, i18n.getId());
			pstmt.setString(index++, i18n.getLbl());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}
	/**
	 * 是否存在这种语言
	 * @param lang 语言
	 * @return true表示数据库存在这个语言，否则返回false
	 */
	public boolean existsLang(String lang) {
		String sql = "select count(1) from i18n where lang=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, lang);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1)>0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
		return false;
	}
	/**
	 * @return 获取系统现有的语言
	 */
	public List<String> getLangs() {
		String sql = "select distinct lang from i18n order by lang";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> results = new ArrayList<String>();
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				results.add(rs.getString("lang"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
		return results;
	}
	/**
	 * @return 键值列表
	 */
	public List<I18n> getIds() {
		String sql = "select id,count(id) total from i18n group by id order by id";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<I18n> results = new ArrayList<I18n>();
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				I18n i18n = new I18n();
				i18n.setId(rs.getString("id"));
				i18n.setTotal(rs.getInt("total"));
				results.add(i18n);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
		return results;
	}
}
