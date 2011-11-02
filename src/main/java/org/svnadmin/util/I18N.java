/**
 * 
 */
package org.svnadmin.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.svnadmin.Constants;
import org.svnadmin.entity.I18n;
import org.svnadmin.service.I18nService;

/**
 * 多语言工具类
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 */
public class I18N {
	
	/**
	 * 缓存
	 */
	private static Map<String,String> cache = new HashMap<String,String>();
	/**
	 * 多语言服务层
	 */
	private static I18nService i18nService = SpringUtils.getBean(I18nService.BEAN_NAME);
	
	/**
	 * 获取默认的语言
	 * @param request 请求
	 * @return 获取默认的语言
	 */
	public static final String getDefaultLang(HttpServletRequest request){
		//from session
		String result = (String) request.getSession().getAttribute(Constants.SESSION_KEY_LANG);
		if(result != null){
			return result;
		}
		//from request local
		Locale locale = request.getLocale();
		String country = locale.getCountry();
		String language = locale.getLanguage();
		
		if(country.length() != 0 && i18nService.existsLang(language+"_"+country)){//数据库是否存在这个语言?
			result = language+"_"+country;
		}
		if(result == null && i18nService.existsLang(language)){//数据库是否存在这个语言?
			result = language;
		}
		if(result == null){
			result = Locale.SIMPLIFIED_CHINESE.toString();//default zh_CN
		}
		
		request.getSession().setAttribute(Constants.SESSION_KEY_LANG, result);//set to session
		return result;
	}
	
	/**
	 * @param request 请求
	 * @param id 语言id
	 * @param defValue 默认值
	 * @return 格式化后的多语言
	 */
	public static final String getLbl(HttpServletRequest request,String id,String defValue){
		return getLbl(getDefaultLang(request), id, defValue, null);
	}
	/**
	 * @param lang 语言
	 * @param id 语言id
	 * @param defValue 默认值
	 * @return 格式化后的多语言
	 */
	public static final String getLbl(String lang,String id,String defValue){
		return getLbl(lang, id, defValue, null);
	}
	/**
	 * @param lang 语言
	 * @param id key
	 * @param defValue 默认值
	 * @param args 参数
	 * @return 格式化后的多语言
	 */
	public static final String getLbl(String lang,String id,String defValue,Object[] args){
		String key = lang+"$"+id;
		//from cache
		if(cache.containsKey(key)){
			return cache.get(key);
		}
		//from database
		I18n i18n = i18nService.getI18n(lang,id);
		if(i18n == null){//数据库里不存在
			i18n = new I18n();
			i18n.setLang(lang);
			i18n.setId(id);
			i18n.setLbl(defValue);
			i18nService.insert(i18n);
		}
		String lbl = i18n.getLbl();
		if(lbl == null){
			lbl = i18n.getId();//没有设置lbl，则取id
		}else if(args!=null){
//			format = new MessageFormat(pattern);
//	        format.setLocale(locale);
//	        format.applyPattern(pattern);
//			str = format.format(args)
			lbl = MessageFormat.format(lbl, args);
		}
		//put into cache
		cache.put(key, lbl);
		return lbl;
	}
	
	/**
	 * 清空缓存
	 */
	public static synchronized void clearCache(){
		cache.clear();
	}

	/**
	 * 提供Service层或DAO使用，会从当前的线程中获取语言
	 * @param id 语言id
	 * @param defValue 默认值
	 * @return 格式化后的多语言
	 * 
	 * @see LangProvider
	 */
	public static final String getLbl(String id,String defValue){
		return getLbl(LangProvider.getCurrentLang(), id, defValue, null);
	}
	/**
	 * 提供Service层或DAO使用，会从当前的线程中获取语言
	 * @param id key
	 * @param defValue 默认值
	 * @param args 参数
	 * @return 格式化后的多语言
	 * 
	 * @see LangProvider
	 */
	public static final String getLbl(String id,String defValue,Object[] args){
		return getLbl(LangProvider.getCurrentLang(), id, defValue, args);
	}

}
