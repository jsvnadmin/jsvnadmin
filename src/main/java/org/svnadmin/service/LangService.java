/**
 * 
 */
package org.svnadmin.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.svnadmin.dao.LangDao;
import org.svnadmin.entity.Lang;

/**
 * 多语言
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 *
 */
@Service(LangService.BEAN_NAME)
public class LangService {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME="langService";

	/**
	 * DAO
	 */
	@Resource(name=LangDao.BEAN_NAME)
	protected LangDao langDao;

	public Lang getLang(String lang){
		return langDao.get(lang);
	}

	/**
	 * @param l
	 */
	@Transactional
	public void insert(Lang lang) {
		langDao.insert(lang);
	}
}
