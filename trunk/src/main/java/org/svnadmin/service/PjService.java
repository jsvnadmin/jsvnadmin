package org.svnadmin.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.svnadmin.Constants;
import org.svnadmin.dao.PjAuthDao;
import org.svnadmin.dao.PjDao;
import org.svnadmin.dao.PjGrDao;
import org.svnadmin.dao.PjGrUsrDao;
import org.svnadmin.dao.PjUsrDao;
import org.svnadmin.entity.Pj;
import org.svnadmin.entity.PjGr;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

@Service(PjService.BEAN_NAME)
public class PjService {
	public static final String BEAN_NAME = "pjService";
	
	@Resource(name=PjDao.BEAN_NAME)
	PjDao pjDao;
	
	@Resource(name=PjUsrDao.BEAN_NAME)
	PjUsrDao pjUsrDao;
	
	@Resource(name=PjGrDao.BEAN_NAME)
	PjGrDao pjGrDao;
	
	@Resource(name=PjGrUsrDao.BEAN_NAME)
	PjGrUsrDao pjGrUsrDao;
	
	@Resource(name=PjAuthDao.BEAN_NAME)
	PjAuthDao pjAuthDao;
	
	@Resource(name=SvnService.BEAN_NAME)
	SvnService svnService;


	public Pj get(String pj) {
		return pjDao.get(pj);
	}
	
	public List<Pj> list() {
		return pjDao.getList();
	}

	public List<Pj> list(String usr) {
		return pjDao.getList(usr);
	}

	@Transactional
	public void delete(String pj) {
		pjAuthDao.deletePj(pj);
		pjGrUsrDao.deletePj(pj);
		pjGrDao.deletePj(pj);
		pjUsrDao.deletePj(pj);
		
		svnService.exportConfig(pj);
		
		pjDao.delete(pj);
	}

	@Transactional
	public void save(String acc,Pj pj) {
		if(StringUtils.isNotBlank(pj.getPath())){
			pj.setPath(StringUtils.replace(pj.getPath(), "\\", "/").toLowerCase());//小写
		}
		if(StringUtils.isNotBlank(pj.getUrl())){
			pj.setUrl(StringUtils.replace(pj.getUrl(), "\\", "/").toLowerCase());//小写
		}
		
		//是否可以增加项目
		boolean insert =pjDao.get(pj.getPj()) == null; 
		if(insert){
			//数据库里已经存在相同的路径或url的项目
			if(this.pjDao.getCount(pj.getPath(), pj.getUrl()) > 0 ){
				throw new RuntimeException("数据库里已经存在相同的路径或url的仓库项目，请检查路径或url");
			}
		}else{
			//数据库里已经存在相同的路径或url的项目
			if(this.pjDao.getCount(pj.getPath(), pj.getUrl()) > 1 ){
				throw new RuntimeException("数据库里已经存在多个相同的路径或url的仓库项目，请检查路径或url");
			}
		}
		
		File respository = new File(pj.getPath());
		if (!respository.exists() || !respository.isDirectory()) {//不存在仓库
			try {
				SVNRepositoryFactory.createLocalRepository(respository, true , false );
			} catch (SVNException e) {
				throw new RuntimeException("创建仓库失败."+pj.getPath()+" "+e.getMessage());
			}
		}
		if (insert) {
			this.pjDao.insert(pj);
			// 增加默认的组
			for (String gr : Constants.GROUPS) {
				PjGr pjGr = new PjGr();
				pjGr.setPj(pj.getPj());
				pjGr.setGr(gr);
				pjGr.setDes(gr);
				pjGrDao.save(pjGr);
			}
		}else{
			this.pjDao.update(pj);
		}
		svnService.exportConfig(pj.getPj());
	}
}
