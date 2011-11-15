package org.tree.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 树节点
 *
 */
public class TreeNode implements Comparable<TreeNode>,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3490509128368051666L;
	/**
	 * 节点显示的文字
	 */
	private String text;
	/**
	 * 点击节点时的url链接
	 */
	private String url;
	/**
	 * 是否是叶子
	 */
	private boolean leaf;
	/**
	 * 父节点
	 */
	private TreeNode parent;
	
	/**
	 * 叶子
	 */
	private List<TreeNode> children;

	
	private Map<String,String> parameters;
	private Map<String,String> attributes;

	public TreeNode() {
	}
	
	public TreeNode(String text){
		this.text=text;
	}
	
	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public List<TreeNode> getChildren() {
		return children;
	}
	
	public void setChildren(List<TreeNode> children) {
		if(children !=null){
			for (TreeNode treeNode : children) {
				treeNode.setParent(this);
			}
		}
		this.children = children;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	public void addParamete(String key,String value){
		if(parameters == null){
			parameters = new HashMap<String,String>();
		}
		parameters.put(key, value);
	}
	
	public void addAttribute(String key,String value){
		if(attributes == null){
			attributes = new HashMap<String,String>();
		}
		attributes.put(key, value);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int compareTo(TreeNode o) {
		if(this.isLeaf()){
			if(o.isLeaf()){
				if(this.getText() == null){
					return -1;
				}else{
					return this.getText().compareToIgnoreCase(o.getText());
				}
			}else{
				return 1;
			}
		}else{
			if(o.isLeaf()){
				return -1;
			}else{
				if(this.getText() == null){
					return -1;
				}else{
					return this.getText().compareToIgnoreCase(o.getText());
				}
			}
		}
		
	}


}
