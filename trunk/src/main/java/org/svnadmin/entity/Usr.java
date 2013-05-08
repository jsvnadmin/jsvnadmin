package org.svnadmin.entity;

import java.io.Serializable;

/**
 * 用户
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * 
 */
public class Usr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8251147689572549482L;
	/**
	 * 用户ID
	 */
	private String usr;
	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 密码(加密)
	 */
	private String psw;
	/**
	 * 角色
	 */
	private String role;

	/**
	 * @return 用户ID
	 */
	public String getUsr() {
		return usr;
	}

	/**
	 * @param usr
	 *            用户ID
	 */
	public void setUsr(String usr) {
		this.usr = usr;
	}

	/**
	 * @return 用户姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            用户姓名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 密码(加密)
	 */
	public String getPsw() {
		return psw;
	}

	/**
	 * @param psw
	 *            密码(加密)
	 */
	public void setPsw(String psw) {
		this.psw = psw;
	}

	/**
	 * @return 角色
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            角色
	 */
	public void setRole(String role) {
		this.role = role;
	}

}
