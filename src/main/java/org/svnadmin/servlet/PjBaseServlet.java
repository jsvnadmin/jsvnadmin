package org.svnadmin.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.svnadmin.Constants;
import org.svnadmin.entity.Usr;
import org.svnadmin.service.PjGrUsrService;
import org.svnadmin.util.SpringUtils;

public class PjBaseServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	PjGrUsrService pjGrUsrService = SpringUtils.getBean(PjGrUsrService.BEAN_NAME);
	
	protected boolean hasManagerRight(HttpServletRequest request,
			HttpServletResponse response) {
		if(this.hasAdminRight(request, response)){
			return true;
		}
		Usr usr = getUsrFromSession(request);
		String pj = request.getParameter("pj");
		if(pj==null){
			return false;
		}
		// TODO delete me 为了兼容3.0版本 see: Issue 4
		String gr = pj+"_"+Constants.GROUP_MANAGER;
		if(this.pjGrUsrService.get(pj, gr, usr.getUsr()) != null){
			return true;
		}
		//3.0.1版本以后
		gr = Constants.GROUP_MANAGER;
		return this.pjGrUsrService.get(pj, gr, usr.getUsr()) != null;
	}
}
