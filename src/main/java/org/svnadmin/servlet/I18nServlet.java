package org.svnadmin.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.svnadmin.entity.I18n;
import org.svnadmin.service.I18nService;
import org.svnadmin.util.I18N;
import org.svnadmin.util.SpringUtils;

/**
 * 多语言
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 */
public class I18nServlet extends BaseServlet {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 9025053134741368489L;
	/**
	 * 服务层
	 */
	protected I18nService i18nService = SpringUtils.getBean(I18nService.BEAN_NAME);

	@Override
	protected void validate(HttpServletRequest request) {
		super.validate(request);
		if(!hasAdminRight(request)){
			throw new RuntimeException(I18N.getLbl(request, "i18n.noright", "没有管理语言的权限"));
		}
	}
	
	@Override
	protected void save(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		int i =0;
		List<I18n> list = new ArrayList<I18n>();
		String lang;
		while((lang = request.getParameter("lang_"+i)) != null){
			I18n i18n =new I18n();
			i18n.setId(id);
			i18n.setLang(lang);
			i18n.setLbl(request.getParameter("lbl_"+i));
			list.add(i18n);
			
			i++;
		}
		i18nService.save(list);
	}

	@Override
	protected void list(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			request.setAttribute("entitys",
					i18nService.getI18ns(id));
		}
		
		List<String> langs = i18nService.getLangs();
		request.setAttribute("langs", langs);
		
		List<I18n> ids = i18nService.getIds();
		request.setAttribute("ids", ids);
	}

	@Override
	protected void forword(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("i18n.jsp").forward(request, response);
	}

}
