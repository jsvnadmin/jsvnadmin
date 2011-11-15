package org.tree.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 树节点
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
public class TreeNode implements Comparable<TreeNode>,Serializable{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -2006855434442859723L;
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

	
	/**
	 * 参数
	 */
	private Map<String,String> parameters;
	/**
	 * 属性
	 */
	private Map<String,String> attributes;

	/**
	 * 默认构造函数
	 */
	public TreeNode() {
	}
	
	/**
	 * 构造函数
	 * @param text 节点显示的文字
	 */
	public TreeNode(String text){
		this.text=text;
	}
	
	/**
	 * @return 父节点
	 */
	public TreeNode getParent() {
		return parent;
	}

	/**
	 * @param parent 父节点
	 */
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	/**
	 * @return 叶子
	 */
	public List<TreeNode> getChildren() {
		return children;
	}
	
	/**
	 * @param children 叶子
	 */
	public void setChildren(List<TreeNode> children) {
		if(children !=null){
			for (TreeNode treeNode : children) {
				treeNode.setParent(this);
			}
		}
		this.children = children;
	}

	/**
	 * @return 是否是叶子
	 */
	public boolean isLeaf() {
		return leaf;
	}

	/**
	 * @param leaf 是否是叶子
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	/**
	 * @return 参数
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters 参数
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return 属性
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes 属性
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * 增加一个参数
	 * @param key 键
	 * @param value 值
	 */
	public void addParamete(String key,String value){
		if(parameters == null){
			parameters = new HashMap<String,String>();
		}
		parameters.put(key, value);
	}
	
	/**
	 * 增加一个属性
	 * @param key 键
	 * @param value 值
	 */
	public void addAttribute(String key,String value){
		if(attributes == null){
			attributes = new HashMap<String,String>();
		}
		attributes.put(key, value);
	}

	/**
	 * @return 节点显示的文字
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text 节点显示的文字
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return 点击节点时的url链接
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url 点击节点时的url链接
	 */
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
