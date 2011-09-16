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
import org.svnadmin.entity.Usr;
import org.svnadmin.util.EncryptUtil;

@Service(UsrService.BEAN_NAME)
public class UsrService {
	
	public static final String BEAN_NAME = "usrService";
	
	@Resource(name=UsrDao.BEAN_NAME)
	UsrDao usrDao;
	
	@Resource(name=PjAuthDao.BEAN_NAME)
	PjAuthDao pjAuthDao;
	
	@Resource(name=PjGrUsrDao.BEAN_NAME)
	PjGrUsrDao pjGrUsrDao;
	
	@Resource(name=PjUsrDao.BEAN_NAME)
	PjUsrDao pjUsrDao;

	@Resource(name=PjDao.BEAN_NAME)
	PjDao pjDao;

	@Resource(name=SvnService.BEAN_NAME)
	SvnService svnService;

	public Usr get(String usr){
		return this.usrDao.get(usr);
	}
	
	public List<Usr> list(){
		return this.usrDao.getList();
	}
	
	public List<Usr> list(String pj){
		return this.usrDao.getList(pj);
	}
	/**
	 * 获取这个项目组未选的用户
	 * @param pj 项目
	 * @param gr 组
	 * @return 项目组未选的用户
	 */
	public List<Usr> listUnSelected(String pj, String gr) {
		return this.usrDao.listUnSelected(pj,gr);
	}
	
	@Transactional
	public void delete(String usr){
		
		List<Pj> list = this.getPjList(usr);
		
		this.pjAuthDao.deleteUsr(usr);
		this.pjGrUsrDao.deleteUsr(usr);
		this.pjUsrDao.deleteUsr(usr);
		this.usrDao.delete(usr);
		
		//更新用户所在的项目
		if(list!=null){
			for (Pj pj : list) {
				this.svnService.exportConfig(pj);
			}
		}
	}
	
	@Transactional
	public void save(Usr usr){
		if(this.usrDao.get(usr.getUsr())==null){
			this.usrDao.insert(usr);
		}else{
			this.usrDao.update(usr);
		}
		//更新用户所在的项目
		List<Pj> list = this.getPjList(usr.getUsr());
		if(list!=null){
			for (Pj pj : list) {
				this.svnService.exportConfig(pj);
			}
		}
	}
	
	private List<Pj> getPjList(String usr){
		List<Pj> list = this.pjDao.getList(usr);//用户可以看到的所有项目
		//如果项目使用http(多库)，只返回一个项目就可以，SvnService导出时，会导出所有相同svn root的项目
		List<Pj> results = new ArrayList<Pj>();
		
		Map<String,Pj> temp = new HashMap<String,Pj>();
		for (Pj pj : list) {
			if(Constants.HTTP_MUTIL.equals(pj.getType())){
				File root = new File(pj.getPath()).getParentFile();//svn root
				String key = root.getAbsolutePath();
				if(temp.containsKey(key)){
					continue;
				}else{
					temp.put(key, pj);
					results.add(pj);//第一个
				}
			}else{
				results.add(pj);
			}
		}
		
		
		return results;
	}
	
	public int getCount(){
		return this.usrDao.getCount();
	}

	@Transactional
	public Usr login(String usr, String psw) {
		int usrCount = this.getCount();
		if(usrCount == 0){//第一次使用，设置管理员
			Usr entity = new Usr();
			entity.setUsr(usr);
			entity.setPsw(EncryptUtil.encrypt(psw));
			entity.setRole(Constants.USR_ROLE_ADMIN);
			this.usrDao.insert(entity);
			//*
			Usr all = new Usr();
			all.setUsr("*");
			all.setPsw("*");
			this.usrDao.insert(all);
			//
			return entity;
		}
		//正常登录
		Usr entity = this.get(usr);
		if (entity == null) {
			throw new RuntimeException("不存在用户 " + usr);
		}
		if (!entity.getPsw().equals(EncryptUtil.encrypt(psw))) {
			throw new RuntimeException("密码错误 ");
		}
		return entity;
	}

}
