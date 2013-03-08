package org.svnadmin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.svnadmin.entity.PjAuth;
import org.svnadmin.service.PjAuthService;
import org.svnadmin.service.PjGrService;
import org.svnadmin.util.SpringUtils;

/**
 * 项目权限
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 * 
 */
public class PjAuthServlet extends PjBaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8684748093607178473L;
	/**
	 * 项目权限服务层
	 */
	PjAuthService pjAuthService = SpringUtils.getBean(PjAuthService.BEAN_NAME);
	/**
	 * 项目组服务层
	 */
	PjGrService pjGrService = SpringUtils.getBean(PjGrService.BEAN_NAME);

	@Override
	protected void before(HttpServletRequest request,
			HttpServletResponse response) {
		this.validateAccessPj(request);
		this.validateManager(request, response);//检查权限
	}

	@Override
	protected void delete(HttpServletRequest request,
			HttpServletResponse response) {
		String pj = request.getParameter("pj");
		String gr = request.getParameter("gr");
		String usr = request.getParameter("usr");
		String res = request.getParameter("res");

		if (StringUtils.isNotBlank(gr)) {
			pjAuthService.deleteByGr(pj, gr, res);
		} else if (StringUtils.isNotBlank(usr)) {
			pjAuthService.deleteByUsr(pj, usr, res);
		}
	}

	@Override
	protected void save(HttpServletRequest request, HttpServletResponse response) {

		String pj = request.getParameter("pj");
		String res = request.getParameter("res");

		String[] grs = request.getParameterValues("grs");
		String[] usrs = request.getParameterValues("usrs");

		String rw = request.getParameter("rw");
		PjAuth entity = new PjAuth();
		entity.setPj(pj);
		entity.setRes(res);
		request.setAttribute("entity", entity);

		pjAuthService.save(pj, res, rw, grs, usrs);
		
	}

	@Override
	protected void list(HttpServletRequest request, HttpServletResponse response) {
		String pj = request.getParameter("pj");
		String res = request.getParameter("res");
		PjAuth entity = (PjAuth) request.getAttribute("entity");
		if(entity == null){
			entity = new PjAuth();
			entity.setPj(pj);
			request.setAttribute("entity", entity);
		}
		
		if(StringUtils.isBlank(res)){
			String path = request.getParameter("path");//从rep 树点击进来，传递的是path
			if(StringUtils.isNotBlank(path)){
				res = this.pjAuthService.formatRes(pj, path);
			}
		}else{
			res = entity.getRes();
		}
		
		entity.setRes(res);
		
		List<PjAuth> list = pjAuthService.list(pj,res);
		request.setAttribute("list", list);
	}

	@Override
	protected void forword(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.setAttribute("usrList", usrService.list());

		request.setAttribute("pjgrlist",
				pjGrService.list(request.getParameter("pj")));

		request.setAttribute("pjreslist",
				pjAuthService.getResList(request.getParameter("pj")));
		request.getRequestDispatcher("pjauth.jsp").forward(request, response);
	}

}
