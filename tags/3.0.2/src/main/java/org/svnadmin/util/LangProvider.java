/**
 * 
 */
package org.svnadmin.util;

/**
 * 为当前的线程提供lang
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
public class LangProvider {
	/**
	 * 
	 */
	private static final ThreadLocal<String> LANG_THREAD_LOCAL = new ThreadLocal<String>();
	
	/**
	 * @return 当前的语言
	 */
	public static String getCurrentLang() {
		String lang = LANG_THREAD_LOCAL.get();
		if(lang == null){
			throw new RuntimeException("当前线程没有设置语言!");
		}
        return lang;
    }

    /**
     * 设置当前的语言到当前线程中
     * @param lang 语言
     */
    public static void setLang(String lang) {
    	LANG_THREAD_LOCAL.set(lang);
    }
   
    /**
     * 清空线程的语言
     */
    public static void removeLang() {
    	LANG_THREAD_LOCAL.remove();
    }
}
