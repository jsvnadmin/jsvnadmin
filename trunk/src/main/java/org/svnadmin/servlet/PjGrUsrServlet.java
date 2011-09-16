package org.svnadmin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.svnadmin.entity.PjGrUsr;
import org.svnadmin.service.UsrService;
import org.svnadmin.util.SpringUtils;

/**
 * 项目组用户
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 * 
 */
public class PjGrUsrServlet extends PjBaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1506192047326005839L;
	/**
	 * 用户服务层
	 */
	protected UsrService usrService = SpringUtils.getBean(UsrService.BEAN_NAME);

	@Override
	protected void before(HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isBlank(request.getParameter("pj"))
				|| StringUtils.isBlank(request.getParameter("gr"))) {
			throw new RuntimeException("不可以直接访问，请从项目的菜单进来这个页面!");
		}
	}

	@Override
	protected void save(HttpServletRequest request, HttpServletResponse response) {
		this.pjGrUsrService.save(request.getParameter("pj"),
				request.getParameter("gr"), request.getParameterValues("usrs"));
	}

	@Override
	protected void delete(HttpServletRequest request,
			HttpServletResponse response) {
		this.pjGrUsrService.delete(request.getParameter("pj"),
				request.getParameter("gr"), request.getParameter("usr"));
	}

	@Override
	protected void list(HttpServletRequest request, HttpServletResponse response) {
		List<PjGrUsr> list = this.pjGrUsrService.list(
				request.getParameter("pj"), request.getParameter("gr"));
		request.setAttribute("list", list);
	}

	@Override
	protected void forword(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		request.setAttribute(
				"usrList",
				usrService.listUnSelected(request.getParameter("pj"),
						request.getParameter("gr")));
		request.getRequestDispatcher("pjgrusr.jsp").forward(request, response);
	}

}
