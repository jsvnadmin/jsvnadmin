package org.svnadmin.entity;

import java.io.Serializable;

/**
 * 权限
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * 
 */
public class PjAuth implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8744287510861451872L;
	/**
	 * 资源
	 */
	private String res;
	/**
	 * 项目
	 */
	private String pj;
	/**
	 * 组
	 */
	private String gr;
	/**
	 * 用户
	 */
	private String usr;
	/**
	 * 用户姓名
	 */
	private String usrName;
	/**
	 * r : 可读; w : 可写
	 */
	private String rw;
	/**
	 * 描述
	 */
	private String des;

	/**
	 * @return 资源
	 */
	public String getRes() {
		return res;
	}

	/**
	 * @param res
	 *            资源
	 */
	public void setRes(String res) {
		this.res = res;
	}

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

	/**
	 * @return r : 可读; w : 可写
	 */
	public String getRw() {
		return rw;
	}

	/**
	 * @param rw
	 *            r : 可读; w : 可写
	 */
	public void setRw(String rw) {
		this.rw = rw;
	}

	/**
	 * @return 用户
	 */
	public String getUsr() {
		return usr;
	}

	/**
	 * @param usr
	 *            用户
	 */
	public void setUsr(String usr) {
		this.usr = usr;
	}

	/**
	 * 
	 * @return 用户姓名
	 */
	public String getUsrName() {
		return usrName;
	}

	/**
	 * 
	 * @param usrName
	 *            用户姓名
	 */
	public void setUsrName(String usrName) {
		this.usrName = usrName;
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
