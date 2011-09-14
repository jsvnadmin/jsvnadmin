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

@Service(PjAuthService.BEAN_NAME)
public class PjAuthService {
	public static final String BEAN_NAME = "pjAuthService";
	
	@Resource(name=PjAuthDao.BEAN_NAME)
	PjAuthDao pjAuthDao;
	
	@Resource(name=PjDao.BEAN_NAME)
	PjDao pjDao;
	
	@Resource(name=SvnService.BEAN_NAME)
	SvnService svnService;


	public List<String> getResList(String pj) {
		return pjAuthDao.getResList(pj);
	}

	public List<PjAuth> list(String pj) {
		return pjAuthDao.getList(pj);
	}

	@Transactional
	public void deleteByGr(String pj, String gr,String res) {
		pjAuthDao.deleteByGr(pj, gr, res);
		svnService.exportConfig(pj);
	}

	@Transactional
	public void deleteByUsr(String pj, String usr,String res) {
		pjAuthDao.deleteByUsr(pj, usr, res);
		svnService.exportConfig(pj);
	}

	@Transactional
	public void save(String pj,String res,String rw, String[] grs, String[] usrs) {
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
