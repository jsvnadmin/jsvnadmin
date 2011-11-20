/**
 * 
 */
package org.svnadmin.entity;

import java.io.Serializable;

/**
 * @author Harvey
 * @since 3.0.2
 *
 */
public class I18n implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8731707774048772734L;
	/**
	 * 语言
	 */
	private String lang;
	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String lbl;
	
	/**
	 * ID对应的语言总数
	 */
	private int total;
	/**
	 * @return 语言
	 */
	public String getLang() {
		return lang;
	}
	/**
	 * @param lang 语言
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
	/**
	 * @return key
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id key
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return label
	 */
	public String getLbl() {
		return lbl;
	}
	/**
	 * @param lbl label
	 */
	public void setLbl(String lbl) {
		this.lbl = lbl;
	}
	/**
	 * @return ID对应的语言总数
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total ID对应的语言总数
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
}
