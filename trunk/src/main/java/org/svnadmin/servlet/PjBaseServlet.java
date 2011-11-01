package org.svnadmin.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.svnadmin.Constants;
import org.svnadmin.entity.Usr;
import org.svnadmin.service.PjGrUsrService;
import org.svnadmin.util.I18N;
import org.svnadmin.util.SpringUtils;

/**
 * 项目相关servlet的父类
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 * 
 */
public class PjBaseServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2582193508726665366L;
	/**
	 * 项目组用户服务层
	 */
	protected PjGrUsrService pjGrUsrService = SpringUtils
			.getBean(PjGrUsrService.BEAN_NAME);

	/**
	 * 当前登录的用户是否有当前项目的管理员权限
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            相应
	 * @return true表示有权限;false表示没有权限.
	 */
	protected boolean hasManagerRight(HttpServletRequest request,
			HttpServletResponse response) {
		if (this.hasAdminRight(request, response)) {
			return true;
		}
		Usr usr = getUsrFromSession(request);
		String pj = request.getParameter("pj");
		if (pj == null) {
			return false;
		}
		// TODO delete me 为了兼容3.0版本 see: Issue 4
		String gr = pj + "_" + Constants.GROUP_MANAGER;
		if (this.pjGrUsrService.get(pj, gr, usr.getUsr()) != null) {
			return true;
		}
		// 3.0.1版本以后
		gr = Constants.GROUP_MANAGER;
		return this.pjGrUsrService.get(pj, gr, usr.getUsr()) != null;
	}
	
	/**
	 * 检查是否有项目管理员的权限
	 * @param request 请求
	 * @param response 响应
	 */
	protected void validateManager(HttpServletRequest request,HttpServletResponse response) {
		if(!this.hasManagerRight(request, response)){
			throw new RuntimeException(I18N.getLbl(request, "sys.error.nomanagerright", "你没有访问权限!"));
		}
	}
}
