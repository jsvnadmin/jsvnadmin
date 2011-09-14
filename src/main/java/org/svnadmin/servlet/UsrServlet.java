package org.svnadmin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.svnadmin.Constants;
import org.svnadmin.entity.Usr;
import org.svnadmin.exceptions.TimeoutException;
import org.svnadmin.service.UsrService;
import org.svnadmin.util.EncryptUtil;
import org.svnadmin.util.SpringUtils;

/**
 * Servlet implementation class UsrServlet
 */
public class UsrServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	UsrService usrService = SpringUtils.getBean(UsrService.BEAN_NAME);

	@Override
	protected void get(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("entity",
				usrService.get(request.getParameter("usr")));
	}

	@Override
	protected void delete(HttpServletRequest request,
			HttpServletResponse response) {
		usrService.delete(request.getParameter("usr"));
		
		if(request.getParameter("usr").equals(getUsrFromSession(request).getUsr())){//当前用户
			request.getSession().removeAttribute(Constants.SESSION_KEY);
			request.getSession().invalidate();
			throw new TimeoutException("没有登录");
		}
	}

	@Override
	protected void save(HttpServletRequest request, HttpServletResponse response) {

		Usr entity = new Usr();
		entity.setUsr(request.getParameter("usr"));
		if (StringUtils.isNotBlank(request.getParameter("newPsw"))) {
			entity.setPsw(EncryptUtil.encrypt(request.getParameter("newPsw")));
		} else {
			entity.setPsw(request.getParameter("psw"));
		}
		entity.setRole(request.getParameter("role"));

		usrService.save(entity);
		
		if(entity.getUsr().equals(getUsrFromSession(request).getUsr())){//当前用户
			request.getSession().setAttribute(Constants.SESSION_KEY,entity);
		}
		request.setAttribute("entity", entity);
	}

	@Override
	protected void list(HttpServletRequest request, HttpServletResponse response) {
		boolean hasAdminRight = this.hasAdminRight(request, response);
		if(hasAdminRight){
			List<Usr> list = usrService.list();
			request.setAttribute("list", list);
		}
	}

	@Override
	protected void forword(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		boolean hasAdminRight = this.hasAdminRight(request, response);
		request.setAttribute("hasAdminRight", hasAdminRight);
		
		if(!hasAdminRight){
			request.setAttribute("entity",getUsrFromSession(request));
		}

		request.getRequestDispatcher("usr.jsp").forward(request, response);
	}

}
