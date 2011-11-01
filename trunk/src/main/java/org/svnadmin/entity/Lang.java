/**
 * 
 */
package org.svnadmin.entity;

import java.io.Serializable;

/**
 * 语言
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */

public class Lang implements Serializable {

	/**
	 * 语言
	 */
	private String lang;
	/**
	 * 描述
	 */
	private String des;

	/**
	 * @return 语言
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang
	 *            语言
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return 描述
	 */
	public String getDes() {
		return des;
	}

	/**
	 * @param des
	 *            描述
	 */
	public void setDes(String des) {
		this.des = des;
	}

}
