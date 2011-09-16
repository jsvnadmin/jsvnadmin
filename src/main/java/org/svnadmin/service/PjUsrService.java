package org.svnadmin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.svnadmin.dao.PjUsrDao;
import org.svnadmin.entity.PjUsr;

/**
 * 项目用户服务层(采用svn或http单库方式是，用户可以对每个项目设置不用的密码)
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 * 
 */
@Service(PjUsrService.BEAN_NAME)
public class PjUsrService {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME = "pjUsrService";

	/**
	 * 项目用户DAO
	 */
	@Resource(name = PjUsrDao.BEAN_NAME)
	protected PjUsrDao pjUsrDao;

	/**
	 * SVN服务层
	 */
	@Resource(name = SvnService.BEAN_NAME)
	protected SvnService svnService;

	/**
	 * @param pj
	 *            项目
	 * @param usr
	 *            用户
	 * @return 项目用户
	 */
	public PjUsr get(String pj, String usr) {
		return pjUsrDao.get(pj, usr);
	}

	/**
	 * @param pj
	 *            项目
	 * @return 项目的用户列表
	 */
	public List<PjUsr> list(String pj) {
		return pjUsrDao.getList(pj);
	}

	/**
	 * 删除
	 * 
	 * @param pj
	 *            项目
	 * @param usr
	 *            用户
	 */
	@Transactional
	public void delete(String pj, String usr) {
		pjUsrDao.delete(pj, usr);

		svnService.exportConfig(pj);
	}

	/**
	 * 保存
	 * 
	 * @param pjUsr
	 *            项目用户
	 */
	@Transactional
	public void save(PjUsr pjUsr) {
		if (this.pjUsrDao.get(pjUsr.getPj(), pjUsr.getUsr()) == null) {
			this.pjUsrDao.insert(pjUsr);
		} else {
			this.pjUsrDao.update(pjUsr);
		}

		svnService.exportConfig(pjUsr.getPj());
	}
}
