package org.svnadmin.exceptions;

/**
 * 超时异常
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * 
 */
public class TimeoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param string
	 *            消息
	 */
	public TimeoutException(String string) {
		super(string);
	}

}
