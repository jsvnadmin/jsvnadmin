package org.svnadmin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.svnadmin.Constants;
import org.svnadmin.service.UsrService;
import org.svnadmin.util.I18N;
import org.svnadmin.util.SpringUtils;

/**
 * 登录
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 * 
 */
public class LoginServlet extends ServletSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2037344827672377791L;

	/**
	 * 用户服务层
	 */
	protected UsrService usrService = SpringUtils.getBean(UsrService.BEAN_NAME);

	@Override
	protected void excute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if ("logout".equals(request.getParameter("act"))) {
			request.getSession().removeAttribute(Constants.SESSION_KEY_USER);
			request.getSession().invalidate();
			response.sendRedirect("login.jsp");
			return;
		}

		String usr = request.getParameter("usr");
		String psw = request.getParameter("psw");
		try {

			if (StringUtils.isBlank(usr)) {
				throw new RuntimeException(I18N.getLbl(request, "login.error.usr.empty", "请输入帐号"));
			}

			request.getSession().setAttribute(Constants.SESSION_KEY_USER,
					usrService.login(usr, psw));

			response.sendRedirect("pj");

		} catch (Exception e) {
			request.setAttribute(Constants.ERROR, e.getMessage());
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
		}

	}

}
