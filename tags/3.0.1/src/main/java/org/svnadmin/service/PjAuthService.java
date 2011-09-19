package org.svnadmin.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.svnadmin.dao.PjAuthDao;
import org.svnadmin.dao.PjDao;
import org.svnadmin.entity.Pj;
import org.svnadmin.entity.PjAuth;

/**
 * 项目权限
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 *
 */
@Service(PjAuthService.BEAN_NAME)
public class PjAuthService {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME = "pjAuthService";
	
	/**
	 * 项目权限DAO
	 */
	@Resource(name=PjAuthDao.BEAN_NAME)
	protected PjAuthDao pjAuthDao;
	
	/**
	 * 项目DAO
	 */
	@Resource(name=PjDao.BEAN_NAME)
	protected PjDao pjDao;
	
	/**
	 * SVN服务层
	 */
	@Resource(name=SvnService.BEAN_NAME)
	protected SvnService svnService;


	/**
	 * @param pj
	 *            项目
	 * @return 项目的资源列表
	 */
	public List<String> getResList(String pj) {
		return pjAuthDao.getResList(pj);
	}

	/**
	 * @param pj
	 *            项目
	 * @return 项目资源的权限列表
	 */
	public List<PjAuth> list(String pj) {
		return pjAuthDao.getList(pj);
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
	@Transactional
	public void deleteByGr(String pj, String gr,String res) {
		pjAuthDao.deleteByGr(pj, gr, res);
		svnService.exportConfig(pj);
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
	@Transactional
	public void deleteByUsr(String pj, String usr,String res) {
		pjAuthDao.deleteByUsr(pj, usr, res);
		svnService.exportConfig(pj);
	}

	/**
	 * 保存
	 * @param pj 项目
	 * @param res 资源
	 * @param rw 可读可写
	 * @param grs 组
	 * @param usrs 用户
	 */
	@Transactional
	public void save(String pj,String res,String rw, String[] grs, String[] usrs) {
		//如果资源没有[],自动加上[pj:/]
		if(!res.startsWith("[") && !res.endsWith("]")){
			Pj pjEntity = this.pjDao.get(pj);
			String pjid = StringUtils.substringAfterLast(pjEntity.getPath(), "/");
			res = "["+pjid+":"+res+"]";
		}
		//gr
		if(grs!=null){
			for (String gr : grs) {
				if(StringUtils.isBlank(gr)){
					continue;
				}
				PjAuth pjAuth = new PjAuth();
				pjAuth.setPj(pj);
				pjAuth.setRes(res);
				pjAuth.setRw(rw);
				pjAuth.setGr(gr);
				pjAuthDao.saveByGr(pjAuth);
			}
		}
		//usr
		if(usrs!=null){
			for (String usr : usrs) {
				if(StringUtils.isBlank(usr)){
					continue;
				}
				PjAuth pjAuth = new PjAuth();
				pjAuth.setPj(pj);
				pjAuth.setRes(res);
				pjAuth.setRw(rw);
				pjAuth.setUsr(usr);
				pjAuthDao.saveByUsr(pjAuth);
			}
		}
		//export
		svnService.exportConfig(pj);
	}
}
