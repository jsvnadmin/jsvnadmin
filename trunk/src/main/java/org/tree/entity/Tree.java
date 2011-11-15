package org.tree.entity;


public class Tree {
	private String id;
	private String parentId;
	private boolean leaf;
	private String treeNodeService;
	
	public Tree() {
	}
	
	public Tree(String id,String parentId,String treeNodeService){
		this.id=id;
		this.parentId=parentId;
		this.treeNodeService = treeNodeService;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getTreeNodeService() {
		return treeNodeService;
	}

	public void setTreeNodeService(String treeNodeService) {
		this.treeNodeService = treeNodeService;
	}


}
