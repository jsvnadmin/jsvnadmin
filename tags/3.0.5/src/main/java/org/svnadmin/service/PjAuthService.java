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
	 * @param res 资源
	 * @return 项目资源的权限列表
	 */
	public List<PjAuth> list(String pj,String res) {
		if(StringUtils.isBlank(res)){
			return pjAuthDao.getList(pj);
		}
		return pjAuthDao.getList(pj,res);
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
		res = this.formatRes(pj, res);//如果资源没有[],自动加上
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
	
	/**
	 * 格式化资源.如果资源没有[],自动加上[relateRoot:/]
	 * @param pj 项目id
	 * @param res 资源
	 * @return 格式化后的资源
	 * @since 3.0.3
	 */
	public String formatRes(String pj,String res){
		//如果资源没有[],自动加上
//		if(!res.startsWith("[") && !res.endsWith("]")){
			return this.formatRes(this.pjDao.get(pj), res);
//		}
//		return res;
	}
	/**
	 * 格式化资源.如果资源没有[],自动加上[relateRoot:/]
	 * @param pj 项目
	 * @param res 资源
	 * @return 格式化后的资源
	 * @since 3.0.3
	 */
	public String formatRes(Pj pj,String res){
		//去除[xxx:]，重新加上[relateRoot:/]，防止跨项目授权
		res = StringUtils.replaceEach(res, new String[]{"[","]"}, new String[]{"",""});
		if (res.indexOf(":")!=-1) {
			res = StringUtils.substringAfter(res, ":");
		}
		
		//如果资源没有[],自动加上
		String relateRoot = PjService.getRelateRootPath(pj);
		if(!res.startsWith("[") && !res.endsWith("]")){
			if(res.startsWith("/")){
				return "["+relateRoot+":"+res+"]";
			}else{
				return  "["+relateRoot+":/"+res+"]";
			}
		}
		return res;
	}
}
