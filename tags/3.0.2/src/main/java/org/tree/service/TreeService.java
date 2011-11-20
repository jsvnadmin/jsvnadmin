/**
 * 
 */
package org.tree.service;

import java.util.Map;

/**
 * 树服务层接口
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
public interface TreeService {
	/**
	 * 树ID
	 */
	public static final String TREE_ID_VAR = "treeId";
	/**
	 * 树的父的ID
	 */
	public static final String TREE_PARENTID_VAR = "treeParentId";
	
	/**
	 * @return 树工厂类
	 */
	TreeFactory getTreeFactory();
	
	/**
	 * @param parameters 参数
	 * @return 树的html
	 */
	String getHTML(Map<String, Object> parameters);
}
