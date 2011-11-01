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
import org.svnadmin.entity.Lang;
import org.svnadmin.entity.LangLbl;
import org.svnadmin.entity.Usr;
import org.svnadmin.service.LangLblService;
import org.svnadmin.service.LangService;
import org.svnadmin.servlet.BaseServlet;

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
	 * 语言服务层
	 */
	private static LangService langService = SpringUtils.getBean(LangService.BEAN_NAME);
	/**
	 * 多语言服务层
	 */
	private static LangLblService langLblService = SpringUtils.getBean(LangLblService.BEAN_NAME);
	
	/**
	 * 获取默认的语言
	 * @param request
	 * @return
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
		
		if(country.length() != 0){
			result = language+"_"+country;
		}else{
			result = language;
		}
		Lang lang = langService.getLang(result);//数据库是否存在这个语言?
		if(lang == null){
			//default zh_CN
			return Locale.SIMPLIFIED_CHINESE.toString();
		}
		
		request.getSession().setAttribute(Constants.SESSION_KEY_LANG, result);//set to session
		return result;
	}
	
	/**
	 * @param request 
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
	 * @param id
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
		LangLbl langLbl = langLblService.getLbl(lang,id);
		if(langLbl == null){//数据库里不存在
			//lang
			Lang l = new Lang();
			l.setLang(lang);
			l.setDes(lang);
			if(langService.getLang(lang) == null){
				langService.insert(l);
			}
			//lang_lbl
			langLbl = new LangLbl();
			langLbl.setLang(lang);
			langLbl.setId(id);
			langLbl.setLbl(defValue);
			langLblService.insert(langLbl);
		}
		String lbl = langLbl.getLbl();
		if(args!=null){
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
	 * @param id
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
