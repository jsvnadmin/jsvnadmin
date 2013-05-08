package org.svnadmin.entity;

import java.io.Serializable;

/**
 * 项目组用户
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * 
 */
public class PjGrUsr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2031955995574649327L;
	/**
	 * 项目
	 */
	private String pj;
	/**
	 * 用户
	 */
	private String usr;
	/**
	 * 用户姓名
	 */
	private String usrName;
	/**
	 * 组
	 */
	private String gr;

	/**
	 * @return 项目
	 */
	public String getPj() {
		return pj;
	}

	/**
	 * @param pj
	 *            项目
	 */
	public void setPj(String pj) {
		this.pj = pj;
	}

	/**
	 * @return 用户
	 */
	public String getUsr() {
		return usr;
	}

	/**
	 * @return 用户姓名
	 */
	public String getUsrName() {
		return usrName;
	}

	/**
	 * @param usrName
	 *            用户姓名
	 */
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	/**
	 * @param usr
	 *            用户
	 */
	public void setUsr(String usr) {
		this.usr = usr;
	}

	/**
	 * @return 组
	 */
	public String getGr() {
		return gr;
	}

	/**
	 * @param gr
	 *            组
	 */
	public void setGr(String gr) {
		this.gr = gr;
	}

}
