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
		String gr = Constants.getManagerGroup(pj);
		return this.pjGrUsrService.get(pj, gr, usr.getUsr()) != null;
	}
}
