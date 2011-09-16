package org.svnadmin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.svnadmin.dao.PjAuthDao;
import org.svnadmin.dao.PjGrDao;
import org.svnadmin.dao.PjGrUsrDao;
import org.svnadmin.entity.PjGr;

/**
 * 项目组服务层
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 * 
 */
@Service(PjGrService.BEAN_NAME)
public class PjGrService {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME = "pjGrService";

	/**
	 * 项目组DAO
	 */
	@Resource(name = PjGrDao.BEAN_NAME)
	protected PjGrDao pjGrDao;

	/**
	 * 项目组用户DAO
	 */
	@Resource(name = PjGrUsrDao.BEAN_NAME)
	protected PjGrUsrDao pjGrUsrDao;

	/**
	 * 项目权限DAO
	 */
	@Resource(name = PjAuthDao.BEAN_NAME)
	protected PjAuthDao pjAuthDao;

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
	 * @return 项目组
	 */
	public PjGr get(String pj, String gr) {
		return pjGrDao.get(pj, gr);
	}

	/**
	 * @param pj
	 *            项目
	 * @return 项目组列表
	 */
	public List<PjGr> list(String pj) {
		return pjGrDao.getList(pj);
	}

	/**
	 * 删除项目组(同时删除项目组的权限，项目组的用户)
	 * 
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 */
	@Transactional
	public void delete(String pj, String gr) {
		pjAuthDao.deletePjGr(pj, gr);
		pjGrUsrDao.deletePjGr(pj, gr);
		pjGrDao.delete(pj, gr);

		svnService.exportConfig(pj);
	}

	/**
	 * 保存
	 * 
	 * @param pjGr
	 *            项目组
	 */
	@Transactional
	public void save(PjGr pjGr) {
		pjGrDao.save(pjGr);
		svnService.exportConfig(pjGr.getPj());
	}
}
