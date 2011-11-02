/**
 * 
 */
package org.svnadmin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.svnadmin.dao.I18nDao;
import org.svnadmin.entity.I18n;

/**
 * 多语言
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 *
 */
@Service(I18nService.BEAN_NAME)
public class I18nService {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME="i18nService";

	/**
	 * DAO
	 */
	@Resource(name=I18nDao.BEAN_NAME)
	protected I18nDao i18nDao;
	
	
	/**
	 * @param lang 语言
	 * @param id key
	 * @return I18n
	 */
	public I18n getI18n(String lang, String id) {
		return i18nDao.get(lang, id);
	}
	/**
	 * @param i18n 多语言
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void insert(I18n i18n) {
		i18nDao.insert(i18n);
	}
	/**
	 * 是否存在这种语言
	 * @param lang 语言
	 * @return true表示数据库存在这个语言，否则返回false
	 */
	public boolean existsLang(String lang) {
		return i18nDao.existsLang(lang);
	}
	
	/**
	 * @return 获取系统现有的语言
	 */
	public List<String> getLangs(){
		return this.i18nDao.getLangs();
	}
}
