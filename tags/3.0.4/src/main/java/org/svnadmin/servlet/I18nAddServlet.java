package org.svnadmin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class I18nAddServlet extends BaseServlet {

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

		I18n entity = new I18n();
		entity.setLang(request.getParameter("lang"));
		entity.setId(entity.getLang());
		entity.setLbl(request.getParameter("lbl"));
		
		request.setAttribute("entity", entity);
		
		if(i18nService.getI18n(entity.getLang(), entity.getId())!=null){
			throw new RuntimeException(I18N.getLbl(request, "i18n.error.duplicatelang", "已经存在相同的语言"));
		}
		//保存
		i18nService.insert(entity);
		
		//当前语言
		I18n entity2 = new I18n();
		entity2.setLang(I18N.getDefaultLang(request));
		entity2.setId(entity.getLang());
		entity2.setLbl(entity.getLbl());
		if(i18nService.getI18n(entity2.getLang(), entity2.getId()) == null){
			i18nService.insert(entity2);
		}
	}

	@Override
	protected void forword(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		List<String> langs = i18nService.getLangs();
		request.setAttribute("langs", langs);
		request.getRequestDispatcher("i18nadd.jsp").forward(request, response);
	}

}
