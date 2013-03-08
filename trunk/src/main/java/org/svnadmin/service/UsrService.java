package org.svnadmin.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.svnadmin.Constants;
import org.svnadmin.dao.PjAuthDao;
import org.svnadmin.dao.PjDao;
import org.svnadmin.dao.PjGrUsrDao;
import org.svnadmin.dao.PjUsrDao;
import org.svnadmin.dao.UsrDao;
import org.svnadmin.entity.Pj;
import org.svnadmin.entity.PjAuth;
import org.svnadmin.entity.Usr;
import org.svnadmin.util.EncryptUtil;
import org.svnadmin.util.I18N;

/**
 * 用户
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0
 * 
 */
@Service(UsrService.BEAN_NAME)
public class UsrService {

	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME = "usrService";

	/**
	 * 用户DAO
	 */
	@Resource(name = UsrDao.BEAN_NAME)
	protected UsrDao usrDao;

	/**
	 * 项目权限DAO
	 */
	@Resource(name = PjAuthDao.BEAN_NAME)
	protected PjAuthDao pjAuthDao;

	/**
	 * 项目组用户DAO
	 */
	@Resource(name = PjGrUsrDao.BEAN_NAME)
	protected PjGrUsrDao pjGrUsrDao;

	/**
	 * 项目用户DAO
	 */
	@Resource(name = PjUsrDao.BEAN_NAME)
	protected PjUsrDao pjUsrDao;

	/**
	 * 项目DAO
	 */
	@Resource(name = PjDao.BEAN_NAME)
	protected PjDao pjDao;

	/**
	 * SVN服务层
	 */
	@Resource(name = SvnService.BEAN_NAME)
	protected SvnService svnService;

	/**
	 * 获取一个用户
	 * 
	 * @param usr
	 *            用户
	 * @return 用户
	 */
	public Usr get(String usr) {
		return this.usrDao.get(usr);
	}

	/**
	 * @return 所有用户列表
	 */
	public List<Usr> list() {
		return this.usrDao.getList();
	}

	/**
	 * @param pj
	 *            项目
	 * @return 所有项目用户列表(不包括*)
	 */
	public List<Usr> list(String pj) {
		return this.usrDao.getList(pj);
	}

	/**
	 * 获取这个项目组未选的用户
	 * 
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 * @return 项目组未选的用户
	 */
	public List<Usr> listUnSelected(String pj, String gr) {
		return this.usrDao.listUnSelected(pj, gr);
	}

	/**
	 * 删除用户(同时删除项目用户，项目组用户，项目用户权限)
	 * 
	 * @param usr
	 *            用户
	 */
	@Transactional
	public void delete(String usr) {

		List<Pj> list = this.getPjList(usr);

		this.pjAuthDao.deleteUsr(usr);
		this.pjGrUsrDao.deleteUsr(usr);
		this.pjUsrDao.deleteUsr(usr);
		this.usrDao.delete(usr);

		// 更新用户所在的项目
		if (list != null) {
			for (Pj pj : list) {
				this.svnService.exportConfig(pj);
			}
		}
	}

	/**
	 * 保存用户
	 * 
	 * @param usr
	 *            用户
	 */
	@Transactional
	public void save(Usr usr) {
		if (this.usrDao.get(usr.getUsr()) == null) {
			this.usrDao.insert(usr);
		} else {
			this.usrDao.update(usr);
		}
		// 更新用户所在的项目
		List<Pj> list = this.getPjList(usr.getUsr());
		if (list != null) {
			for (Pj pj : list) {
				this.svnService.exportConfig(pj);
			}
		}
	}

	/**
	 * @param usr
	 *            用户
	 * @return 用户的项目
	 */
	private List<Pj> getPjList(String usr) {
		List<Pj> list = this.pjDao.getList(usr);// 用户可以看到的所有项目
		// 如果项目使用http(多库)，只返回一个项目就可以，SvnService导出时，会导出所有相同svn root的项目
		List<Pj> results = new ArrayList<Pj>();

		Map<String, Pj> temp = new HashMap<String, Pj>();
		for (Pj pj : list) {
			if (Constants.HTTP_MUTIL.equals(pj.getType())) {
				File root = new File(pj.getPath()).getParentFile();// svn root
				String key = root.getAbsolutePath();
				if (temp.containsKey(key)) {
					continue;
				} else {
					temp.put(key, pj);
					results.add(pj);// 第一个
				}
			} else {
				results.add(pj);
			}
		}

		return results;
	}

	/**
	 * @return 总数(不包括*)
	 */
	public int getCount() {
		return this.usrDao.getCount();
	}

	/**
	 * 登录.如果用户表没有数据，表示第一次使用，输入的用户当作是超级管理员。
	 * 
	 * @param usr
	 *            用户
	 * @param psw
	 *            密码
	 * @return 成功则返回用户的信息，失败抛出异常
	 */
	@Transactional
	public Usr login(String usr, String psw) {
		int usrCount = this.getCount();
		if (usrCount == 0) {// 第一次使用，设置管理员
			Usr entity = new Usr();
			entity.setUsr(usr);
			entity.setPsw(EncryptUtil.encrypt(psw));
			entity.setRole(Constants.USR_ROLE_ADMIN);
			this.usrDao.insert(entity);
			// *
			Usr all = new Usr();
			all.setUsr("*");
			all.setPsw("*");
			this.usrDao.insert(all);
			//
			return entity;
		}
		// 正常登录
		Usr entity = this.get(usr);
		if (entity == null) {
			throw new RuntimeException(I18N.getLbl("login.error.notfoundusr", "不存在用户{0} ",new Object[]{usr}));
		}
		if (!entity.getPsw().equals(EncryptUtil.encrypt(psw))) {
			throw new RuntimeException(I18N.getLbl("login.error.wrongpassword", "密码错误 "));
		}
		return entity;
	}
	
	/**
	 * 验证是否连接数据库成功 Issue 12
	 * @throws Exception 验证失败异常
	 */
	public void validatConnection() throws Exception{
		this.usrDao.validatConnection();
	}
	
	/**
	 * 是否有管理员的权限
	 * @param usr 用户
	 * @return 有管理员权限返回true,否则返回false
	 */
	public boolean hasAdminRight(Usr usr){
		if (Constants.USR_ROLE_ADMIN.equals(usr.getRole())) {
			return true;
		}
		return false;
	}
	/**
	 * 报表:获取用户所有的权限
	 * @param usr 用户
	 * @return 用户所有的权限
	 */
	public List<PjAuth> getAuths(String usr){
		return this.pjAuthDao.getByUsr(usr);
	}
}
