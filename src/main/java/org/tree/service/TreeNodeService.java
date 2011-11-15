package org.tree.service;

import java.util.Map;

import org.tree.entity.Tree;

public interface TreeNodeService {
	
	StringBuffer getHTML(Tree tree, Map<String, Object> parameters);
	
}
