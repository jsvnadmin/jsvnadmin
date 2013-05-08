package org.svnadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.svnadmin.Constants;
import org.svnadmin.entity.PjAuth;

/**
 * 项目资源的权限DAO
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 * 
 */
@Repository(PjAuthDao.BEAN_NAME)
public class PjAuthDao extends Dao {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME = "pjAuthDao";

	/**
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 * @param res
	 *            资源
	 * @return 项目组资源的权限
	 */
	public PjAuth getByGr(String pj, String gr, String res) {
		String sql = "select pj,res,rw,gr,' ' usr,' ' usrname from pj_gr_auth where pj = ? and gr=? and res=? ";

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

	/**
	 * @param pj
	 *            项目
	 * @param usr
	 *            用户
	 * @param res
	 *            资源
	 * @return 项目用户资源的权限
	 */
	public PjAuth getByUsr(String pj, String usr, String res) {
		String sql = "select a.pj,a.res,a.rw,b.usr,b.name as usrname,' ' gr from pj_usr_auth a left join usr b on (a.usr=b.usr) where a.pj = ? and a.usr=? and a.res=? ";

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
	/**
	 * @param usr
	 *            用户
	 * @return 用户的权限
	 */
	public List<PjAuth> getByUsr(String usr) {
		String sql = "select b.pj,p.des,b.usr,b.res,b.rw from usr a";
		sql+=" join pj_usr_auth b on (a.usr = b.usr)";
		sql+=" join pj p on (b.pj=p.pj)";
		sql+=" where a.usr=?";

		sql+=" union all";

		sql+=" select c.pj,p.des,a.usr,c.res,c.rw from usr a";
		sql+=" join pj_gr_usr b on (a.usr = b.usr)";
		sql+=" join pj_gr_auth c on (b.pj = c.pj and b.gr = c.gr)";
		sql+=" join pj p on (b.pj=p.pj)";
		sql+=" where a.usr=?";

		sql+=" order by 1,4";//TODO 为了兼容sqlserver
		
		List<PjAuth> list = new ArrayList<PjAuth>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, usr);
			pstmt.setString(index++, usr);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				
				PjAuth result = new PjAuth();
				result.setPj(rs.getString("pj"));
				result.setDes(rs.getString("des"));
				result.setUsr(rs.getString("usr"));
				result.setRes(rs.getString("res"));
				String rw = rs.getString("rw");
				if (StringUtils.isBlank(rw)) {
					rw = "";
				}
				result.setRw(rw);
				
				list.add(result);
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
	 * @param res 资源
	 * @return 项目资源的权限列表
	 */
	public List<PjAuth> getList(String pj,String res) {
		String sql = "select pj,res,rw,gr,' ' usr,' ' usrname from pj_gr_auth where pj=? and res = ? "
				+ " UNION "
				+ " select a.pj,a.res,a.rw,' ' gr,a.usr,b.name as usrname from pj_usr_auth a left join usr b on (a.usr=b.usr) where a.pj=? and a.res = ? "
				+ " order by res,gr,usr";
		List<PjAuth> list = new ArrayList<PjAuth>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj);
			pstmt.setString(index++, res);
			pstmt.setString(index++, pj);
			pstmt.setString(index++, res);

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
	/**
	 * @param pj
	 *            项目
	 * @return 项目资源的权限列表
	 */
	public List<PjAuth> getList(String pj) {
		String sql = "select pj,res,rw,gr,' ' usr,' ' usrname from pj_gr_auth where pj=? "
				+ " UNION "
				+ " select a.pj,a.res,a.rw,' ' gr,a.usr,b.name as usrname from pj_usr_auth a left join usr b on (a.usr = b.usr) where a.pj=? "
				+ " order by res,gr,usr";
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

	/**
	 * @param rootPath
	 *            svn root path
	 * @return 具有相同svn root的项目资源的权限列表
	 */
	public List<PjAuth> getListByRootPath(String rootPath) {
		String sql = "select pj,res,rw,gr,' ' usr,' ' usrname from pj_gr_auth where pj in (select distinct pj from pj where type=? and path like ?) "
				+ " UNION "
				+ " select a.pj,a.res,a.rw,' ' gr,a.usr,b.name usrname from pj_usr_auth a left join usr b on (a.usr=b.usr) where a.pj in (select distinct pj from pj where type=? and path like ?) "
				+ " order by res,gr,usr";
		List<PjAuth> list = new ArrayList<PjAuth>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, Constants.HTTP_MUTIL);
			pstmt.setString(index++, rootPath + "%");//TODO 大小写敏感?
			pstmt.setString(index++, Constants.HTTP_MUTIL);
			pstmt.setString(index++, rootPath + "%");//TODO 大小写敏感?

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

	/**
	 * @param rs
	 *            ResultSet
	 * @return 项目资源的权限
	 * @throws SQLException
	 *             jdbc异常
	 */
	PjAuth readPjAuth(ResultSet rs) throws SQLException {
		PjAuth result = new PjAuth();
		result.setPj(rs.getString("pj"));
		result.setGr(rs.getString("gr"));
		result.setUsr(rs.getString("usr"));
		result.setUsrName(rs.getString("usrname"));
		result.setRes(rs.getString("res"));
		String rw = rs.getString("rw");
		if (StringUtils.isBlank(rw)) {
			rw = "";
		}
		result.setRw(rw);

		return result;
	}

	/**
	 * 删除项目 组资源的权限
	 * 
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 * @param res
	 *            资源
	 */
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

	/**
	 * 删除项目用户资源的权限
	 * 
	 * @param pj
	 *            项目
	 * @param usr
	 *            用户
	 * @param res
	 *            资源
	 */
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

	/**
	 * 删除项目 资源的权限
	 * 
	 * @param pj
	 *            项目
	 */
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

	/**
	 * 删除项目 组资源的权限
	 * 
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 */
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

	/**
	 * 删除用户的项目资源的权限
	 * 
	 * @param usr
	 *            用户
	 */
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

	/**
	 * 保存项目组权限
	 * 
	 * @param pjAuth
	 *            项目组权限
	 */
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

	/**
	 * 保存项目用户权限
	 * 
	 * @param pjAuth
	 *            项目用户权限
	 */
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

	/**
	 * @param pj
	 *            项目
	 * @return 项目的资源列表
	 */
	public List<String> getResList(String pj) {
		String sql = "select distinct res from pj_gr_auth where pj=? "
				+ " UNION select distinct res from pj_usr_auth where pj=? order by res";

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