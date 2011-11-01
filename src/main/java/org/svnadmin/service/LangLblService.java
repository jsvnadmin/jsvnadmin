/**
 * 
 */
package org.svnadmin.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.svnadmin.dao.LangLblDao;
import org.svnadmin.entity.LangLbl;

/**
 * 多语言
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 *
 */
@Service(LangLblService.BEAN_NAME)
public class LangLblService {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME="langLblService";

	/**
	 * DAO
	 */
	@Resource(name=LangLblDao.BEAN_NAME)
	protected LangLblDao langLblDao;
	
	public LangLbl getLbl(String lang, String id) {
		return langLblDao.get(lang, id);
	}
	/**
	 * @param langLbl
	 */
	@Transactional
	public void insert(LangLbl langLbl) {
		langLblDao.insert(langLbl);
	}
}
