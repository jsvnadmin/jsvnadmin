package org.svnadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.svnadmin.Constants;
import org.svnadmin.entity.PjAuth;

@Repository(PjAuthDao.BEAN_NAME)
public class PjAuthDao extends Dao {
	private final Logger LOG = Logger.getLogger(this.getClass());
	public static final String BEAN_NAME = "pjAuthDao";

	public PjAuth getByGr(String pj, String gr, String res) {
		String sql = "select pj,res,rw,gr,' ' usr from pj_gr_auth where pj = ? and gr=? and res=? ";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj);
			pstmt.setString(index++, gr);
			pstmt.setString(index++, res);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return readPjAuth(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
		return null;
	}

	public PjAuth getByUsr(String pj, String usr, String res) {
		String sql = "select pj,res,rw,usr,' ' gr from pj_usr_auth where pj = ? and usr=? and res=? ";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj);
			pstmt.setString(index++, usr);
			pstmt.setString(index++, res);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return readPjAuth(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
		return null;
	}

	public List<PjAuth> getList(String pj) {
		String sql = "select pj,res,rw,gr,' ' usr from pj_gr_auth where pj=? "
				+ "UNION "
				+ "select pj,res,rw,' ' gr,usr from pj_usr_auth where pj=? "
				+ "order by res,gr,usr";
		List<PjAuth> list = new ArrayList<PjAuth>();

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
				list.add(readPjAuth(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
	}

	public List<PjAuth> getListByRootPath(String rootPath) {
		String sql = "select pj,res,rw,gr,' ' usr from pj_gr_auth where pj in (select distinct pj from pj where type=? and path like ?) "
				+ "UNION "
				+ "select pj,res,rw,' ' gr,usr from pj_usr_auth where pj in (select distinct pj from pj where type=? and path like ?) "
				+ "order by res,gr,usr";
		List<PjAuth> list = new ArrayList<PjAuth>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, Constants.HTTP_MUTIL);
			pstmt.setString(index++, rootPath + "%");
			pstmt.setString(index++, Constants.HTTP_MUTIL);
			pstmt.setString(index++, rootPath + "%");

			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(readPjAuth(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
	}

	PjAuth readPjAuth(ResultSet rs) throws SQLException {
		PjAuth result = new PjAuth();
		result.setPj(rs.getString("pj"));
		result.setGr(rs.getString("gr"));
		result.setUsr(rs.getString("usr"));
		result.setRes(rs.getString("res"));
		String rw = rs.getString("rw");
		if (StringUtils.isBlank(rw)) {
			rw = "";
		}
		result.setRw(rw);

		return result;
	}

	public void deleteByGr(String pj, String gr, String res) {
		String sql = "delete from pj_gr_auth where pj = ? and gr=? and res=? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj);
			pstmt.setString(index++, gr);
			pstmt.setString(index++, res);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	public void deleteByUsr(String pj, String usr, String res) {
		String sql = "delete from pj_usr_auth where pj = ? and usr=? and res=? ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj);
			pstmt.setString(index++, usr);
			pstmt.setString(index++, res);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	public void deletePj(String pj) {
		// pj_gr_auth
		String sql = "delete from pj_gr_auth where pj = ?";
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
		// pj_usr_auth
		sql = "delete from pj_usr_auth where pj = ?";
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

	public void deletePjGr(String pj, String gr) {
		String sql = "delete from pj_gr_auth where pj = ? and gr=?";
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

	public void deleteUsr(String usr) {
		String sql = "delete from pj_usr_auth where usr=?";
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

	public void saveByGr(PjAuth pjAuth) {
		if (this.getByGr(pjAuth.getPj(), pjAuth.getGr(), pjAuth.getRes()) == null) {
			String sql = "insert into pj_gr_auth (pj,gr,res,rw) values (?,?,?,?)";

			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = this.getConnection();
				pstmt = conn.prepareStatement(sql);
				int index = 1;
				pstmt.setString(index++, pjAuth.getPj());
				pstmt.setString(index++, pjAuth.getGr());
				pstmt.setString(index++, pjAuth.getRes());
				pstmt.setString(index++, pjAuth.getRw());

				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				this.close(null, pstmt, conn);
			}
		} else {
			String sql = "update pj_gr_auth set rw=? where pj=? and gr=? and res=?";

			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = this.getConnection();
				pstmt = conn.prepareStatement(sql);
				int index = 1;
				pstmt.setString(index++, pjAuth.getRw());
				pstmt.setString(index++, pjAuth.getPj());
				pstmt.setString(index++, pjAuth.getGr());
				pstmt.setString(index++, pjAuth.getRes());

				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				this.close(null, pstmt, conn);
			}
		}
	}

	public void saveByUsr(PjAuth pjAuth) {
		if (this.getByUsr(pjAuth.getPj(), pjAuth.getUsr(), pjAuth.getRes()) == null) {
			String sql = "insert into pj_usr_auth (pj,usr,res,rw) values (?,?,?,?)";

			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = this.getConnection();
				pstmt = conn.prepareStatement(sql);
				int index = 1;
				pstmt.setString(index++, pjAuth.getPj());
				pstmt.setString(index++, pjAuth.getUsr());
				pstmt.setString(index++, pjAuth.getRes());
				pstmt.setString(index++, pjAuth.getRw());

				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				this.close(null, pstmt, conn);
			}
		} else {
			String sql = "update pj_usr_auth set rw=? where pj=? and usr=? and res=?";

			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = this.getConnection();
				pstmt = conn.prepareStatement(sql);
				int index = 1;
				pstmt.setString(index++, pjAuth.getRw());
				pstmt.setString(index++, pjAuth.getPj());
				pstmt.setString(index++, pjAuth.getUsr());
				pstmt.setString(index++, pjAuth.getRes());

				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				this.close(null, pstmt, conn);
			}
		}
	}

	public List<String> getResList(String pj) {
		String sql = "select distinct res from pj_gr_auth where pj=? "
				+ "UNION select distinct res from pj_usr_auth where pj=? order by res";

		List<String> list = new ArrayList<String>();
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
				list.add(rs.getString("res"));

			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}

	}

}