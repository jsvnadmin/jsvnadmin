package org.svnadmin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.svnadmin.dao.PjUsrDao;
import org.svnadmin.entity.PjUsr;

@Service(PjUsrService.BEAN_NAME)
public class PjUsrService {
	public static final String BEAN_NAME = "pjUsrService";

	@Resource(name=PjUsrDao.BEAN_NAME)
	PjUsrDao pjUsrDao;
	
	@Resource(name=SvnService.BEAN_NAME)
	SvnService svnService;
	
	public PjUsr get(String pj, String usr) {
		return pjUsrDao.get(pj, usr);
	}
	
	public List<PjUsr> list(String pj) {
		return pjUsrDao.getList(pj);
	}

	@Transactional
	public void delete(String pj, String usr) {
		pjUsrDao.delete(pj, usr);
		
		svnService.exportConfig(pj);
	}

	@Transactional
	public void save(PjUsr pjUsr) {
		if(this.pjUsrDao.get(pjUsr.getPj(), pjUsr.getUsr())==null){
			this.pjUsrDao.insert(pjUsr);
		}else{
			this.pjUsrDao.update(pjUsr);
		}
		
		svnService.exportConfig(pjUsr.getPj());
	}
}
