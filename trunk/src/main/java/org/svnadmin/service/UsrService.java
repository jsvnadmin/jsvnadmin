package org.svnadmin.service;

import java.util.List;

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
	
	@Transactional
	public void delete(String usr){
		
		List<Pj> list = this.pjDao.getList(usr);
		
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
		List<Pj> list = this.pjDao.getList(usr.getUsr());
		if(list!=null){
			for (Pj pj : list) {
				this.svnService.exportConfig(pj);
			}
		}
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
