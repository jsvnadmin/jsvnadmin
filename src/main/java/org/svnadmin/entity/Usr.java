package org.svnadmin.entity;

/**
 * 用户
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * 
 */
public class Usr {
	/**
	 * 用户ID
	 */
	private String usr;
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
