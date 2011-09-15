package org.svnadmin;


/**
 * 常量
 * @author Harvey
 *
 */
public class Constants {
	/**
	 * 用户在session中id
	 */
	public static final String SESSION_KEY = "_session_key_";
	
	/**
	 * 
	 */
	public static final String ERROR = "error";

	/**
	 * svn协议
	 */
	public static final String SVN = "svn";
	/**
	 * http单库
	 */
	public static final String HTTP = "http";
	/**
	 * http多库
	 */
	public static final String HTTP_MUTIL = "http-mutil";

	/**
	 * 管理组
	 */
	public static final String GROUP_MANAGER = "manager";

	/**
	 * 项目默认的组
	 */
	public static final String[] GROUPS = { GROUP_MANAGER, "developer", "tester" };
	
	/**
	 * 管理员角色代码
	 */
	public static final String USR_ROLE_ADMIN="admin";
	
}
