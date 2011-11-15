package org.tree.service;

import java.util.List;

import org.tree.entity.Tree;

public interface TreeFactory {

	Tree find(String id);

	/**
	 * 直接子树
	 * 
	 * @param parentId
	 * @return
	 */
	List<Tree> findChildren(String parentId);

	TreeNodeService findTreeNodeService(Tree tree);

}
