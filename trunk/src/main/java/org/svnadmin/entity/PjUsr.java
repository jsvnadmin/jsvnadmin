package org.svnadmin.entity;

/**
 * 项目用户
 * 
 * @author Harvey
 * 
 */
public class PjUsr extends Usr {
	/**
	 * 项目ID
	 */
	private String pj;

	/**
	 * @return 项目ID
	 */
	public String getPj() {
		return pj;
	}

	/**
	 * @param pj
	 *            项目ID
	 */
	public void setPj(String pj) {
		this.pj = pj;
	}

}
