package org.svnadmin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.svnadmin.Constants;
import org.svnadmin.entity.Usr;
import org.svnadmin.exceptions.TimeoutException;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected Logger LOG = Logger.getLogger(BaseServlet.class);


	public static Usr getUsrFromSession(HttpServletRequest request) {
		return getUsrFromSession(request.getSession());
	}

	public static Usr getUsrFromSession(PageContext pageContext) {
		return getUsrFromSession(pageContext.getSession());
	}

	public static Usr getUsrFromSession(HttpSession session) {
		try {
			return (Usr) session.getAttribute(Constants.SESSION_KEY);
		} catch (Exception e) {
			return null;
		}
	}

	protected void error(HttpServletRequest request, String errorMessage) {
		request.setAttribute(Constants.ERROR, errorMessage);
	}

	protected void validate(HttpServletRequest request) {
		if (request.getSession().getAttribute(Constants.SESSION_KEY) == null) {
			throw new TimeoutException("没有登录");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			this.validate(request);

			this.before(request, response);


			if (this.isDel(request)) {
				this.delete(request, response);
			} else if (this.isSave(request)) {
				this.save(request, response);
			} else if (this.isGet(request)) {
				this.get(request, response);
			}

			this.list(request, response);
			this.forword(request, response);
		} catch (TimeoutException e) {
			// e.printStackTrace();
			this.error(request, e.getMessage());
			// response.sendRedirect("login.jsp");
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
		} catch (Throwable e) {
			e.printStackTrace();
			if (e.getMessage() == null) {
				StringWriter sWriter = new StringWriter();
				PrintWriter pWriter = new PrintWriter(sWriter);
				e.printStackTrace(pWriter);

				LOG.error(sWriter.toString());
				this.error(request, sWriter.toString());
			} else {
				LOG.error(e + " " + e.getMessage());
				this.error(request, e.getMessage());
			}
			this.list(request, response);
			this.forwordInput(request, response);
		}
	}

	protected void before(HttpServletRequest request,
			HttpServletResponse response) {

	}

	protected void forwordInput(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		this.forword(request, response);
	}

	protected String getFunCode() {
		return "";
	}

	protected void list(HttpServletRequest request, HttpServletResponse response) {

	}

	protected void save(HttpServletRequest request, HttpServletResponse response) {

	}

	protected void delete(HttpServletRequest request,
			HttpServletResponse response) {

	}

	protected void get(HttpServletRequest request, HttpServletResponse response) {

	}

	protected void forword(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

	}

	protected boolean isSave(HttpServletRequest request) {
		return "save".equals(request.getParameter("act"));
	}

	protected boolean isDel(HttpServletRequest request) {
		return "del".equals(request.getParameter("act"));
	}

	protected boolean isGet(HttpServletRequest request) {
		return "get".equals(request.getParameter("act"));
	}
	
	protected boolean hasAdminRight(HttpServletRequest request,
			HttpServletResponse response) {
		Usr usr = getUsrFromSession(request);
		if(Constants.USR_ROLE_ADMIN.equals(usr.getRole())){
			return true;
		}
		return false;
	}

}
