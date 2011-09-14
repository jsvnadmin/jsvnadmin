package org.svnadmin.entity;

/**
 * 权限
 * 
 * @author Harvey
 * 
 */
public class PjAuth {
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
	 * r : 可读; w : 可写
	 */
	private String rw;

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

}
