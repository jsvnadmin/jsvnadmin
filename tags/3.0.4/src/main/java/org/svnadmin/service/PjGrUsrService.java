package org.svnadmin.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.svnadmin.Constants;
import org.svnadmin.dao.PjGrUsrDao;
import org.svnadmin.entity.PjGrUsr;
import org.svnadmin.entity.Usr;

/**
 * 项目组用户服务层
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 * 
 */
@Service(PjGrUsrService.BEAN_NAME)
public class PjGrUsrService {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME = "pjGrUsrService";
	/**
	 * 项目组用户DAO
	 */
	@Resource(name = PjGrUsrDao.BEAN_NAME)
	protected PjGrUsrDao pjGrUsrDao;

	/**
	 * SVN服务层
	 */
	@Resource(name = SvnService.BEAN_NAME)
	protected SvnService svnService;

	/**
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 * @param usr
	 *            用户
	 * @return 组用户
	 */
	public PjGrUsr get(String pj, String gr, String usr) {
		return pjGrUsrDao.get(pj, gr, usr);
	}

	/**
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 * @return 组用户列表
	 */
	public List<PjGrUsr> list(String pj, String gr) {
		return pjGrUsrDao.getList(pj, gr);
	}

	/**
	 * 保存
	 * 
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 * @param usrs
	 *            用户
	 */
	@Transactional
	public void save(String pj, String gr, String[] usrs) {

		if (usrs == null || usrs.length == 0) {
			return;
		}

		for (String usr : usrs) {
			if (StringUtils.isBlank(usr)) {
				continue;
			}
			PjGrUsr pjGrUsr = new PjGrUsr();
			pjGrUsr.setPj(pj);
			pjGrUsr.setGr(gr);
			pjGrUsr.setUsr(usr);
			pjGrUsrDao.save(pjGrUsr);
		}
		// export
		svnService.exportConfig(pj);
	}

	/**
	 * 删除
	 * 
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 * @param usr
	 *            用户
	 */
	@Transactional
	public void delete(String pj, String gr, String usr) {
		pjGrUsrDao.delete(pj, gr, usr);
		svnService.exportConfig(pj);
	}

	/**
	 * 是否有项目管理员的权限
	 * @param usr 用户
	 * @param pj 项目
	 * @return 有权限返回true,否则返回false
	 */
	public boolean hasManagerRight(Usr usr,String pj){
		if (pj == null) {
			return false;
		}
		// TODO delete me 为了兼容3.0版本 see: Issue 4
		String gr = pj + "_" + Constants.GROUP_MANAGER;
		if (this.get(pj, gr, usr.getUsr()) != null) {
			return true;
		}
		// 3.0.1版本以后
		gr = Constants.GROUP_MANAGER;
		return this.get(pj, gr, usr.getUsr()) != null;
	}
}
