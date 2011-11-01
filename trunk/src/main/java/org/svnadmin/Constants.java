package org.svnadmin;

/**
 * 常量
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 1.0
 */
public class Constants {
	/**
	 * lang 保存在session中得key
	 */
	public static final String SESSION_KEY_LANG = "_session_key_lang_";
	/**
	 * 用户在session中key
	 */
	public static final String SESSION_KEY_USER = "_session_key_user_";

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
	public static final String[] GROUPS = { GROUP_MANAGER, "developer",
			"tester" };

	/**
	 * 管理员角色代码
	 */
	public static final String USR_ROLE_ADMIN = "admin";

}
