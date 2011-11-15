/**
 * 
 */
package org.tree.service;

import java.util.Map;

/**
 *
 */
public interface TreeService {
	public static final String TREE_ID_VAR = "treeId";
	public static final String TREE_PARENTID_VAR = "treeParentId";
	
	TreeFactory getTreeFactory();
	
	String getHTML(Map<String, Object> parameters);
}
