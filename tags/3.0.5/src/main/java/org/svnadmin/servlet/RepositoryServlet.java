package org.svnadmin.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.svnadmin.entity.Pj;
import org.svnadmin.service.PjService;
import org.svnadmin.service.RepositoryService;
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
		this.validateAccessPj(request);
		this.validateManager(request, response);//检查权限
	}

	@Override
	protected void unknow(HttpServletRequest request,
			HttpServletResponse response) {
		Pj pj = pjService.get(request.getParameter("pj"));
		String root = repositoryService.getRepositoryRoot(pj);
		String svnUrl = RepositoryService.parseURL(pj.getUrl());
		String path = "/";
		if(root != null){
			try {
				root = URLDecoder.decode(root,"UTF-8");//@see issue 34
			} catch (UnsupportedEncodingException e) {
			}
			if(svnUrl.indexOf(root) != -1){
				path = StringUtils.substringAfter(svnUrl, root);
				if(!path.startsWith("/")){
					path = "/"+path;
				}
			}
		}else{
			root = svnUrl;
		}
		
		request.setAttribute("root", root);
		request.setAttribute("path", path);
	}
	
	@Override
	protected void forword(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("rep.jsp").forward(request, response);
	}

}
