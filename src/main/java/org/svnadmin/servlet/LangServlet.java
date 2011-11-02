package org.svnadmin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.svnadmin.Constants;
import org.svnadmin.service.I18nService;
import org.svnadmin.util.SpringUtils;

/**
 * 转换语言
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
public class LangServlet extends ServletSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4206233408784602306L;

	/**
	 * 用户服务层
	 */
	protected I18nService i18nService = SpringUtils.getBean(I18nService.BEAN_NAME);

	@Override
	protected void excute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		if ("change".equals(request.getParameter("act"))) {
			String lang = request.getParameter("lang");
			if(lang !=null && lang.length()>0){
				request.getSession().setAttribute(Constants.SESSION_KEY_LANG, lang);//保存到当前session
			}
		}
		
		List<String> langs = i18nService.getLangs();
		request.setAttribute("langs", langs);
		
		request.getRequestDispatcher("lang.jsp").forward(request, response);

	}

}
