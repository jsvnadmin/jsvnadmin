package org.svnadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.svnadmin.entity.Usr;

/**
 * 用户DAO
 * 
 * @author Harvey
 * 
 */
@Repository(UsrDao.BEAN_NAME)
public class UsrDao extends Dao {
	/**
	 * 日志
	 */
	private final Logger LOG = Logger.getLogger(this.getClass());

	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME = "usrDao";

	/**
	 * 获取一个用户
	 * 
	 * @param usr
	 *            用户
	 * @return 用户
	 */
	public Usr get(String usr) {
		String sql = "select usr,psw,role from usr where usr=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, usr);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return readUsr(rs);
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
	 * @return 所有用户列表
	 */
	public List<Usr> getList() {
		String sql = "select usr,psw,role from usr order by usr";
		List<Usr> list = new ArrayList<Usr>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(readUsr(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
	}

	/**
	 * @param pj
	 *            项目
	 * @return 所有项目用户列表(不包括*)
	 */
	public List<Usr> getList(String pj) {
		String sql = "select p.usr,p.role,CASE WHEN pu.psw IS NOT NULL THEN pu.psw ELSE p.psw END psw from ("
				+ "select a.usr,a.role,a.psw from usr a "
				+ "where "
				+ "exists (select d.usr from pj_gr_usr d where d.usr=a.usr and d.pj=?) "
				+ "or exists(select c.usr from pj_usr_auth c where a.usr=c.usr and c.pj=?) "
				+ ") p "
				+ "left join pj_usr pu on (p.usr=pu.usr) where p.usr <> '*'"
				+ "order by p.usr; ";

		List<Usr> list = new ArrayList<Usr>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj);
			pstmt.setString(index++, pj);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(readUsr(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
	}

	/**
	 * @param rootPath
	 *            项目所在的svn root
	 * @return 所有相同svn root的项目的用户列表(不包括*)
	 */
	public List<Usr> getListByRootPath(String rootPath) {
		String sql = "select p.usr,p.role,CASE WHEN pu.psw IS NOT NULL THEN pu.psw ELSE p.psw END psw from ("
				+ "select a.usr,a.role,a.psw from usr a "
				+ "where "
				+ "exists (select d.usr from pj_gr_usr d where d.usr=a.usr and d.pj in (select distinct pj from pj where path like ?)) "
				+ "or exists(select c.usr from pj_usr_auth c where a.usr=c.usr and c.pj in (select distinct pj from pj where path like ?)) "
				+ ") p "
				+ "left join pj_usr pu on (p.usr=pu.usr) where p.usr <> '*'"
				+ "order by p.usr; ";

		List<Usr> list = new ArrayList<Usr>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, rootPath + "%");
			pstmt.setString(index++, rootPath + "%");

			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(readUsr(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
	}

	/**
	 * 从ResultSet中读取Usr对象
	 * 
	 * @param rs
	 *            ResultSet
	 * @return Usr对象
	 * @throws SQLException
	 *             JDBC异常
	 */
	Usr readUsr(ResultSet rs) throws SQLException {
		Usr result = new Usr();
		result.setUsr(rs.getString("usr"));
		result.setPsw(rs.getString("psw"));
		result.setRole(rs.getString("role"));
		return result;
	}

	/**
	 * 删除用户
	 * 
	 * @param usr
	 *            用户
	 */
	public void delete(String usr) {
		String sql = "delete from usr where usr=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, usr);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	/**
	 * 更新用户
	 * 
	 * @param usr
	 *            用户
	 * @return 更新数量
	 */
	public int update(Usr usr) {
		String sql = "update usr set psw=?,role=? where usr=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, usr.getPsw());
			pstmt.setString(index++, usr.getRole());
			pstmt.setString(index++, usr.getUsr());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	/**
	 * 增加一个用户
	 * 
	 * @param usr
	 *            用户
	 * @return 更新数量
	 */
	public int insert(Usr usr) {
		String sql = "insert into usr (usr,psw,role) values (?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, usr.getUsr());
			pstmt.setString(index++, usr.getPsw());
			pstmt.setString(index++, usr.getRole());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	/**
	 * @return 总数(不包括*)
	 */
	public int getCount() {
		String sql = "select count(1) from usr where usr <> '*'";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
		return 0;
	}
}