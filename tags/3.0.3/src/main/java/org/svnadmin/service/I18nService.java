/**
 * 
 */
package org.svnadmin.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
	 * @param id 键值
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
	 * 更新
	 * @param i18n 多语言
	 */
	@Transactional
	public void update(I18n i18n){
		i18nDao.update(i18n);
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
	/**
	 * @param id 键值
	 * @return 相同键值的语言列表
	 */
	public Map<String,I18n> getI18ns(String id) {
		return i18nDao.getI18ns(id);
	}
	/**
	 * @return 键值列表
	 */
	public List<I18n> getIds() {
		return this.i18nDao.getIds();
	}
	/**
	 * 获取导出的脚步语句
	 * @return 多语言列表
	 */
	public String getInsertInto() {
		List<I18n> i18nList = this.i18nDao.getList();
		if(i18nList!=null && !i18nList.isEmpty()){
			StringBuffer content = new StringBuffer();
			for (I18n i18n : i18nList) {
				content.append("insert into i18n (lang,id,lbl) values (");
				content.append("'").append(StringUtils.replace(i18n.getLang(), "'", "''")).append("',");
				content.append("'").append(StringUtils.replace(i18n.getId(), "'", "''")).append("',");
				content.append("'").append(StringUtils.replace(i18n.getLbl(), "'", "''")).append("'");
				content.append(");\n");
			}
			return content.toString();
		}
		return null;
	}
	/**
	 * @param list 保存的列表
	 */
	@Transactional
	public void save(List<I18n> list) {
		for (I18n i18n : list) {
			this.save(i18n);
		}
	}
	
	/**
	 * 保存
	 * @param i18n 语言
	 */
	@Transactional
	public void save(I18n i18n){
		if(this.getI18n(i18n.getLang(), i18n.getId())!=null){//已经存在
			this.update(i18n);
		}else{
			this.insert(i18n);
		}
	}
}
