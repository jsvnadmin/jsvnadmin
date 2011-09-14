package org.svnadmin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.svnadmin.dao.PjAuthDao;
import org.svnadmin.dao.PjGrDao;
import org.svnadmin.dao.PjGrUsrDao;
import org.svnadmin.entity.PjGr;

@Service(PjGrService.BEAN_NAME)
public class PjGrService {
	public static final String BEAN_NAME = "pjGrService";
	
	@Resource(name=PjGrDao.BEAN_NAME)
	PjGrDao pjGrDao;
	
	@Resource(name=PjGrUsrDao.BEAN_NAME)
	PjGrUsrDao pjGrUsrDao;
	
	@Resource(name=PjAuthDao.BEAN_NAME)
	PjAuthDao pjAuthDao;
	
	@Resource(name=SvnService.BEAN_NAME)
	SvnService svnService;
	
	public PjGr get(String pj, String gr) {
		return pjGrDao.get(pj, gr);
	}

	public List<PjGr> list(String pj) {
		return pjGrDao.getList(pj);
	}

	@Transactional
	public void delete(String pj, String gr) {
		pjAuthDao.deletePjGr(pj, gr);
		pjGrUsrDao.deletePjGr(pj, gr);
		pjGrDao.delete(pj, gr);

		svnService.exportConfig(pj);
	}

	@Transactional
	public void save(PjGr pjGr) {
		pjGrDao.save(pjGr);
		svnService.exportConfig(pjGr.getPj());
	}
}
