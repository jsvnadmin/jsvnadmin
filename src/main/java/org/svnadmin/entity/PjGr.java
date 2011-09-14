package org.svnadmin.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 组
 * 
 * @author Harvey
 * 
 */
public class PjGr {
	/**
	 * 项目
	 */
	private String pj;
	/**
	 * 组
	 */
	private String gr;
	/**
	 * 描述
	 */
	private String des;

	/**
	 * 组的用户列表
	 */
	private List<PjGrUsr> pjGrUsrs = new ArrayList<PjGrUsr>();

	/**
	 * @return 组的用户列表
	 */
	public List<PjGrUsr> getPjGrUsrs() {
		return pjGrUsrs;
	}

	/**
	 * @param pjGrUsrs
	 *            组的用户列表
	 */
	public void setPjGrUsrs(List<PjGrUsr> pjGrUsrs) {
		this.pjGrUsrs = pjGrUsrs;
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
