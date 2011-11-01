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
import org.svnadmin.util.I18N;

/**
 * Servlet 的父类
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 * 
 */
public class BaseServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8455605645997264637L;
	/**
	 * 日志
	 */
	protected Logger LOG = Logger.getLogger(BaseServlet.class);

	/**
	 * 获取当前登录的用户
	 * 
	 * @param request
	 *            请求
	 * @return 当前登录的用户
	 */
	public static Usr getUsrFromSession(HttpServletRequest request) {
		return getUsrFromSession(request.getSession());
	}

	/**
	 * 获取当前登录的用户
	 * 
	 * @param pageContext
	 *            PageContext
	 * @return 当前登录的用户
	 */
	public static Usr getUsrFromSession(PageContext pageContext) {
		return getUsrFromSession(pageContext.getSession());
	}

	/**
	 * 获取当前登录的用户
	 * 
	 * @param session
	 *            会话
	 * @return 当前登录的用户
	 */
	public static Usr getUsrFromSession(HttpSession session) {
		try {
			return (Usr) session.getAttribute(Constants.SESSION_KEY_USER);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 保存错误信息
	 * 
	 * @param request
	 *            请求
	 * @param errorMessage
	 *            错误消息
	 */
	protected void error(HttpServletRequest request, String errorMessage) {
		request.setAttribute(Constants.ERROR, errorMessage);
	}

	/**
	 * 验证请求
	 * 
	 * @param request
	 *            请求
	 */
	protected void validate(HttpServletRequest request) {
		if (request.getSession().getAttribute(Constants.SESSION_KEY_USER) == null) {
			throw new TimeoutException(I18N.getLbl(request,"sys.timeout","超时或未登录"));
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

	/**
	 * 开始业务逻辑前执行
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	protected void before(HttpServletRequest request,
			HttpServletResponse response) {

	}

	/**
	 * 跳转到成功视图
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @throws IOException
	 *             IO异常
	 * @throws ServletException
	 *             Servlet异常
	 */
	protected void forword(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

	}

	/**
	 * 跳转到输入视图
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @throws IOException
	 *             IO异常
	 * @throws ServletException
	 *             Servlet异常
	 */
	protected void forwordInput(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		this.forword(request, response);
	}

	/**
	 * 列表
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	protected void list(HttpServletRequest request, HttpServletResponse response) {

	}

	/**
	 * 保存
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	protected void save(HttpServletRequest request, HttpServletResponse response) {

	}

	/**
	 * 删除
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	protected void delete(HttpServletRequest request,
			HttpServletResponse response) {

	}

	/**
	 * 获取实体类
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	protected void get(HttpServletRequest request, HttpServletResponse response) {

	}

	/**
	 * @param request
	 *            请求
	 * @return 请求是否是保存
	 */
	protected boolean isSave(HttpServletRequest request) {
		return "save".equals(request.getParameter("act"));
	}

	/**
	 * @param request
	 *            请求
	 * @return 请求是否是删除
	 */
	protected boolean isDel(HttpServletRequest request) {
		return "del".equals(request.getParameter("act"));
	}

	/**
	 * @param request
	 *            请求
	 * @return 请求是否是获取实体类
	 */
	protected boolean isGet(HttpServletRequest request) {
		return "get".equals(request.getParameter("act"));
	}

	/**
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @return 当前登录的用户是否有管理员角色
	 * @see Constants#USR_ROLE_ADMIN
	 * @see Usr#getRole()
	 */
	protected boolean hasAdminRight(HttpServletRequest request,
			HttpServletResponse response) {
		Usr usr = getUsrFromSession(request);
		if (Constants.USR_ROLE_ADMIN.equals(usr.getRole())) {
			return true;
		}
		return false;
	}

}
