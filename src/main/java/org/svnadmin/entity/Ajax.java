package org.svnadmin.entity;

import java.io.Serializable;

/**
 * ajax服务层返回的结果
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 */
public class Ajax implements Serializable{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -545103916156321718L;
	/**
	 * content type
	 */
	private String contentType;
	/**
	 * 结果
	 */
	private String result;
	
	/**
	 * 默认构造函数
	 */
	public Ajax() {
	}
	
	
	/**
	 * 构造函数
	 * @param contentType content type
	 * @param result 结果
	 */
	public Ajax(String contentType,String result){
		this.contentType=contentType;
		this.result=result;
	}
	
	/**
	 * @return content type
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * @param contentType content type
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * @return 结果
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result 结果
	 */
	public void setResult(String result) {
		this.result = result;
	}
}
