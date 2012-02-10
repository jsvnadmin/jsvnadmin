package org.svnadmin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.svnadmin.entity.Pj;
import org.svnadmin.service.PjService;
import org.svnadmin.util.SpringUtils;

/**
 * 项目
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 * 
 */
public class PjServlet extends PjBaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6996739562959142169L;
	/**
	 * 项目服务层
	 */
	protected PjService pjService = SpringUtils.getBean(PjService.BEAN_NAME);

	@Override
	protected void get(HttpServletRequest request, HttpServletResponse response) {
		this.validateManager(request, response);//检查权限
		request.setAttribute("entity",
				pjService.get(request.getParameter("pj")));
	}

	@Override
	protected void delete(HttpServletRequest request,
			HttpServletResponse response) {
		this.validateManager(request, response);//检查权限
		pjService.delete(request.getParameter("pj"));
	}

	@Override
	protected void save(HttpServletRequest request, HttpServletResponse response) {
		this.validateManager(request, response);//检查权限
		Pj entity = new Pj();
		entity.setPj(request.getParameter("pj"));
		entity.setPath(request.getParameter("path"));
		entity.setUrl(request.getParameter("url"));
		entity.setDes(request.getParameter("des"));
		entity.setType(request.getParameter("type"));
		request.setAttribute("entity", entity);
		pjService.save(entity);

	}

	@Override
	protected void list(HttpServletRequest request, HttpServletResponse response) {
		boolean hasAdminRight = hasAdminRight(request);
		List<Pj> list = null;
		if (hasAdminRight) {
			list = pjService.list();// 所有项目
		} else {
			list = pjService.list(getUsrFromSession(request).getUsr());// 登录用户可以看到的项目
		}
		request.setAttribute("list", list);
	}

	@Override
	protected void forword(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		boolean hasAdminRight = hasAdminRight(request);
		request.setAttribute("hasAdminRight", hasAdminRight);

		boolean hasManagerRight = this.hasManagerRight(request, response);
		request.setAttribute("hasManagerRight", hasManagerRight);

		request.getRequestDispatcher("pj.jsp").forward(request, response);
	}

}
