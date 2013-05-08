package org.svnadmin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.svnadmin.Constants;
import org.svnadmin.entity.PjAuth;
import org.svnadmin.entity.Usr;
import org.svnadmin.exceptions.TimeoutException;
import org.svnadmin.util.EncryptUtil;
import org.svnadmin.util.I18N;

/**
 * 用户
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0
 */
public class UsrServlet extends BaseServlet {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 5448367307094487885L;

	@Override
	protected void get(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("entity",
				usrService.get(request.getParameter("usr")));
	}

	@Override
	protected void delete(HttpServletRequest request,
			HttpServletResponse response) {
		if(!hasAdminRight(request)){
			throw new RuntimeException(I18N.getLbl(request, "usr.error.delete.noright", "你没有权限删除用户!"));
		}
		usrService.delete(request.getParameter("usr"));

		if (request.getParameter("usr").equals(
				getUsrFromSession(request).getUsr())) {// 当前用户
			request.getSession().removeAttribute(Constants.SESSION_KEY_USER);
			request.getSession().invalidate();
			throw new TimeoutException(I18N.getLbl(request, "usr.info.relogin", "重新登录"));
		}
	}

	@Override
	protected void save(HttpServletRequest request, HttpServletResponse response) {

		Usr entity = new Usr();
		entity.setUsr(request.getParameter("usr"));
		entity.setName(request.getParameter("name"));
		if (StringUtils.isNotBlank(request.getParameter("newPsw"))) {
			entity.setPsw(EncryptUtil.encrypt(request.getParameter("newPsw")));
		} else {
			entity.setPsw(request.getParameter("psw"));
		}
		entity.setRole(request.getParameter("role"));
		request.setAttribute("entity", entity);

		usrService.save(entity);

		if (entity.getUsr().equals(getUsrFromSession(request).getUsr())) {// 当前用户
			request.getSession().setAttribute(Constants.SESSION_KEY_USER, entity);
		}
	}

	@Override
	protected void list(HttpServletRequest request, HttpServletResponse response) {
		boolean hasAdminRight = hasAdminRight(request);
		if (hasAdminRight) {
			List<Usr> list = usrService.list();
			request.setAttribute("list", list);
		}
	}

	@Override
	protected void forword(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		boolean hasAdminRight = hasAdminRight(request);
		request.setAttribute("hasAdminRight", hasAdminRight);

		Usr sessionUsr = getUsrFromSession(request);
		if (!hasAdminRight) {
			request.setAttribute("entity", sessionUsr);
		}
		//查看用户权限
		String usr = request.getParameter("usr");
		if(StringUtils.isBlank(usr)){
			usr = sessionUsr.getUsr();
		}
		boolean hasUsr = StringUtils.isNotBlank(usr);
		if (hasUsr) {
			List<PjAuth> auths = this.usrService.getAuths(usr);
			request.setAttribute("auths", auths);
		}

		request.getRequestDispatcher("usr.jsp").forward(request, response);
	}

}
