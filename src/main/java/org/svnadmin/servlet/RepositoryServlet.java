package org.svnadmin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.svnadmin.entity.Pj;
import org.svnadmin.service.PjService;
import org.svnadmin.service.RepositoryService;
import org.svnadmin.util.I18N;
import org.svnadmin.util.SpringUtils;

/**
 * 浏览项目仓库
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
public class RepositoryServlet extends PjBaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4665738577299334713L;
	/**
	 * 仓库服务层
	 */
	PjService pjService = SpringUtils.getBean(PjService.BEAN_NAME);
	/**
	 * 仓库服务层
	 */
	RepositoryService repositoryService = SpringUtils.getBean(RepositoryService.BEAN_NAME);

	@Override
	protected void before(HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isBlank(request.getParameter("pj"))) {
			throw new RuntimeException(I18N.getLbl(request, "sys.error.pj.empty", "不可以直接访问，请从项目的菜单进来这个页面!"));
		}
		this.validateManager(request, response);//检查权限
	}

	@Override
	protected void forword(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		Pj pj = pjService.get(request.getParameter("pj"));
		String root = repositoryService.getRepositoryRoot(pj);
		String path = "/";
		if(root != null){
			if(pj.getUrl().indexOf(root) != -1){
				path = StringUtils.substringAfter(pj.getUrl(), root);
				if(!path.startsWith("/")){
					path = "/"+path;
				}
			}
		}else{
			root = pj.getUrl();
		}
		
		request.setAttribute("root", root);
		request.setAttribute("path", path);

		request.getRequestDispatcher("rep.jsp").forward(request, response);
	}

}
