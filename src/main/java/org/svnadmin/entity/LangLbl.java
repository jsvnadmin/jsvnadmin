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
public class LangLbl implements Serializable {
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
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return
	 */
	public String getLbl() {
		return lbl;
	}
	/**
	 * @param lbl
	 */
	public void setLbl(String lbl) {
		this.lbl = lbl;
	}
	
}
