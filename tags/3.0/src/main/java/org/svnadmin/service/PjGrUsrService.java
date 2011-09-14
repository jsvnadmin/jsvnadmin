package org.svnadmin.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.svnadmin.dao.PjGrUsrDao;
import org.svnadmin.entity.PjGrUsr;

@Service(PjGrUsrService.BEAN_NAME)
public class PjGrUsrService {
	public static final String BEAN_NAME = "pjGrUsrService";
	@Resource(name=PjGrUsrDao.BEAN_NAME)
	PjGrUsrDao pjGrUsrDao;
 
	
	@Resource(name=SvnService.BEAN_NAME)
	SvnService svnService;
	
	public PjGrUsr get(String pj, String gr,String usr) {
		return pjGrUsrDao.get(pj, gr,usr);
	}
	
	public List<PjGrUsr> list(String pj, String gr) {
		return pjGrUsrDao.getList(pj, gr);
	}

	@Transactional
	public void save(String pj, String gr, String usr) {
		PjGrUsr pjGrUsr = new PjGrUsr();
		pjGrUsr.setPj(pj);
		pjGrUsr.setGr(gr);
		pjGrUsr.setUsr(usr);
		pjGrUsrDao.save(pjGrUsr);

		svnService.exportConfig(pj);
	}
	@Transactional
	public void save(String pj, String gr,String[] usrs) {
		
		if(usrs==null || usrs.length == 0){
			return;
		}
		
		for (String usr : usrs) {
			if(StringUtils.isBlank(usr)){
				continue;
			}
			PjGrUsr pjGrUsr = new PjGrUsr();
			pjGrUsr.setPj(pj);
			pjGrUsr.setGr(gr);
			pjGrUsr.setUsr(usr);
			pjGrUsrDao.save(pjGrUsr);
		}
		//export
		svnService.exportConfig(pj);
	}

	@Transactional
	public void delete(String pj, String gr, String usr) {
		pjGrUsrDao.delete(pj, gr, usr);
		svnService.exportConfig(pj);
	}

}
