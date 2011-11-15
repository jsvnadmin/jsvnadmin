/**
 * 
 */
package org.svnadmin.util;

import org.svnadmin.entity.Usr;

/**
 * 为当前的线程提供登录的用户
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
public class UsrProvider {
	/**
	 * 
	 */
	private static final ThreadLocal<Usr> USR_THREAD_LOCAL = new ThreadLocal<Usr>();
	
	/**
	 * @return 当前的用户
	 */
	public static Usr getCurrentUsr() {
		Usr usr = USR_THREAD_LOCAL.get();
		if(usr == null){
			throw new RuntimeException("当前线程没有设置用户!");
		}
        return usr;
    }

    /**
     * 设置当前的用户到当前线程中
     * @param usr 用户
     */
    public static void setUsr(Usr usr) {
    	USR_THREAD_LOCAL.set(usr);
    }
   
    /**
     * 清空线程的用户
     */
    public static void removeUsr() {
    	USR_THREAD_LOCAL.remove();
    }
}
