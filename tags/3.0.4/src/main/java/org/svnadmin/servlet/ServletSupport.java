/**
 * 
 */
package org.svnadmin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.svnadmin.util.I18N;
import org.svnadmin.util.LangProvider;
import org.svnadmin.util.UsrProvider;

/**
 * servlet的基类
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
public abstract class ServletSupport extends HttpServlet{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4025985104182878768L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			LangProvider.setLang(I18N.getDefaultLang(request));
			UsrProvider.setUsr(BaseServlet.getUsrFromSession(request));
			this.excute(request, response);
		}finally{
			LangProvider.removeLang();
			UsrProvider.removeUsr();
		}
	}

	/**
	 * @param request 请求
	 * @param response 响应
	 * @throws ServletException 异常
	 * @throws IOException IO异常
	 */
	protected abstract void excute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;

}