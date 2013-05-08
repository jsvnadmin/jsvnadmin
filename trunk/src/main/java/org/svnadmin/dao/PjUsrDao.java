package org.svnadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.svnadmin.entity.PjUsr;

/**
 * 
 * 项目的用户。 只对单库方式有用,包括svn协议和http协议(单库)，可以每个项目设置用户的密码。
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * 
 */
@Repository(PjUsrDao.BEAN_NAME)
public class PjUsrDao extends Dao {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME = "pjUsrDao";

	/**
	 * @param pj
	 *            项目
	 * @param usr
	 *            用户
	 * @return 项目用户
	 */
	public PjUsr get(String pj, String usr) {
		String sql = "select a.pj,a.usr,a.psw,b.name as usrname from pj_usr a left join usr b on (a.usr = b.usr) where a.pj = ? and a.usr=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj);
			pstmt.setString(index++, usr);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return readPjUsr(rs);
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
	 * @param pj
	 *            项目
	 * @return 项目的用户列表
	 */
	public List<PjUsr> getList(String pj) {
		String sql = "select a.pj,a.usr,a.psw,b.name usrname from pj_usr a left join usr b on (a.usr = b.usr) where a.pj = ?";
		List<PjUsr> list = new ArrayList<PjUsr>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(readPjUsr(rs));
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
	 * @param rs
	 *            ResultSet
	 * @return PjUsr
	 * @throws SQLException
	 *             jdbc异常
	 */
	PjUsr readPjUsr(ResultSet rs) throws SQLException {
		PjUsr result = new PjUsr();
		result.setPj(rs.getString("pj"));
		result.setUsr(rs.getString("usr"));
		result.setName(rs.getString("usrname"));
		result.setPsw(rs.getString("psw"));
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param pj
	 *            项目
	 * @param usr
	 *            用户
	 */
	public void delete(String pj, String usr) {
		String sql = "delete from pj_usr where pj = ? and usr=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj);
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
	 * 删除这个项目的用户
	 * 
	 * @param pj
	 *            项目
	 */
	public void deletePj(String pj) {
		String sql = "delete from pj_usr where pj = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	/**
	 * 删除用户
	 * 
	 * @param usr
	 *            用户
	 */
	public void deleteUsr(String usr) {
		String sql = "delete from pj_usr where usr = ?";
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
	 * 增加一条记录
	 * 
	 * @param pjUsr
	 *            项目用户
	 * @return 成功数量
	 */
	public int insert(PjUsr pjUsr) {
		String sql = "insert into pj_usr (pj,usr,psw) values (?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pjUsr.getPj());
			pstmt.setString(index++, pjUsr.getUsr());
			pstmt.setString(index++, pjUsr.getPsw());

			return pstmt.executeUpdate();
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
	 * @param pjUsr
	 *            项目用户
	 * @return 更新的数量
	 */
	public int update(PjUsr pjUsr) {
		String sql = "update pj_usr set psw=? where pj = ? and usr=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pjUsr.getPsw());
			pstmt.setString(index++, pjUsr.getPj());
			pstmt.setString(index++, pjUsr.getUsr());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

}