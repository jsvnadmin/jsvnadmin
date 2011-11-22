package org.svnadmin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.svnadmin.entity.PjUsr;
import org.svnadmin.entity.Usr;
import org.svnadmin.service.PjUsrService;
import org.svnadmin.util.EncryptUtil;
import org.svnadmin.util.SpringUtils;

/**
 * 项目用户(采用svn或http单库方式是，用户可以对每个项目设置不用的密码)
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 */
public class PjUsrServlet extends PjServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4020326530937387440L;

	/**
	 * 项目用户服务层
	 */
	protected PjUsrService pjUsrService = SpringUtils
			.getBean(PjUsrService.BEAN_NAME);

	@Override
	protected void before(HttpServletRequest request,
			HttpServletResponse response) {
		this.validateAccessPj(request);
	}

	@Override
	protected void get(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(
				"entity",
				pjUsrService.get(request.getParameter("pj"),
						request.getParameter("usr")));
	}

	@Override
	protected void delete(HttpServletRequest request,
			HttpServletResponse response) {
		pjUsrService.delete(request.getParameter("pj"),
				request.getParameter("usr"));
	}

	@Override
	protected void save(HttpServletRequest request, HttpServletResponse response) {

		PjUsr entity = new PjUsr();
		entity.setPj(request.getParameter("pj"));
		entity.setUsr(request.getParameter("usr"));
		if (StringUtils.isNotBlank(request.getParameter("newPsw"))) {
			entity.setPsw(EncryptUtil.encrypt(request.getParameter("newPsw")));
		} else {
			entity.setPsw(request.getParameter("psw"));
		}
		request.setAttribute("entity", entity);

		pjUsrService.save(entity);
	}

	@Override
	protected void list(HttpServletRequest request, HttpServletResponse response) {
		List<PjUsr> list = pjUsrService.list(request.getParameter("pj"));
		request.setAttribute("list", list);
	}

	@Override
	protected void forword(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		boolean hasManagerRight = this.hasManagerRight(request, response);
		request.setAttribute("hasManagerRight", hasManagerRight);

		if (hasManagerRight) {
			// 账户
			List<Usr> usrList = usrService.list(request.getParameter("pj"));
			request.setAttribute("usrList", usrList);
		} else {
			PjUsr entity = pjUsrService.get(request.getParameter("pj"),
					getUsrFromSession(request).getUsr());
			if (entity == null) {
				entity = new PjUsr();
				entity.setPj(request.getParameter("pj"));
				entity.setUsr(getUsrFromSession(request).getUsr());
			}
			request.setAttribute("entity", entity);
		}

		request.getRequestDispatcher("pjusr.jsp").forward(request, response);
	}

}
