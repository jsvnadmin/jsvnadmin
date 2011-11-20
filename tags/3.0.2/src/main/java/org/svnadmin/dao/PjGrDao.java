package org.svnadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.svnadmin.entity.PjGr;

/**
 * 项目组
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * 
 */
@Repository(PjGrDao.BEAN_NAME)
public class PjGrDao extends Dao {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME = "pjGrDao";

	/**
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 * @return 项目组
	 */
	public PjGr get(String pj, String gr) {
		String sql = "select pj,gr,des from pj_gr where pj = ? and gr=?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj);
			pstmt.setString(index++, gr);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return readPjGr(rs);
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
	 * @return 项目组列表
	 */
	public List<PjGr> getList(String pj) {
		String sql = "select pj,gr,des from pj_gr where pj=? order by pj,gr";
		List<PjGr> list = new ArrayList<PjGr>();

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
				list.add(readPjGr(rs));
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
	 * @return 项目组
	 * @throws SQLException
	 *             jdbc异常
	 */
	PjGr readPjGr(ResultSet rs) throws SQLException {
		PjGr result = new PjGr();
		result.setPj(rs.getString("pj"));
		result.setGr(rs.getString("gr"));
		result.setDes(rs.getString("des"));
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 */
	public void delete(String pj, String gr) {
		String sql = "delete from pj_gr where pj = ? and gr=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj);
			pstmt.setString(index++, gr);

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
	 * 
	 * @param pj
	 *            项目
	 */
	public void deletePj(String pj) {
		String sql = "delete from pj_gr where pj = ?";
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
	 * 保存
	 * 
	 * @param pjGr
	 *            项目组
	 */
	public void save(PjGr pjGr) {
		if (this.get(pjGr.getPj(), pjGr.getGr()) == null) {
			String sql = "insert into pj_gr (pj,gr,des) values (?,?,?)";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = this.getConnection();
				pstmt = conn.prepareStatement(sql);
				int index = 1;
				pstmt.setString(index++, pjGr.getPj());
				pstmt.setString(index++, pjGr.getGr());
				pstmt.setString(index++, pjGr.getDes());

				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				this.close(null, pstmt, conn);
			}
		} else {
			String sql = "update pj_gr set des=? where pj = ? and gr=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = this.getConnection();
				pstmt = conn.prepareStatement(sql);
				int index = 1;
				pstmt.setString(index++, pjGr.getDes());
				pstmt.setString(index++, pjGr.getPj());
				pstmt.setString(index++, pjGr.getGr());

				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				this.close(null, pstmt, conn);
			}
		}
	}

}