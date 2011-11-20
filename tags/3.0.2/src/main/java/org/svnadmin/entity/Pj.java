package org.svnadmin.entity;

import java.io.Serializable;

/**
 * 项目
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * 
 */
public class Pj implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3570692240378418844L;
	/**
	 * 项目ID
	 */
	private String pj;
	/**
	 * 仓库位置
	 */
	private String path;
	/**
	 * 访问项目的svn地址
	 */
	private String url;
	/**
	 * 描述
	 */
	private String des;
	/**
	 * 类型
	 */
	private String type;

	/**
	 * 用户是否是这个项目的管理员
	 */
	private boolean manager;

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

	/**
	 * @return 仓库位置
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            仓库位置
	 */
	public void setPath(String path) {
		this.path = path;
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
	 * @return 类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return 访问项目的svn地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            访问项目的svn地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return 用户是否是这个项目的管理员
	 */
	public boolean isManager() {
		return manager;
	}

	/**
	 * @param manager
	 *            用户是否是这个项目的管理员
	 */
	public void setManager(boolean manager) {
		this.manager = manager;
	}

}
