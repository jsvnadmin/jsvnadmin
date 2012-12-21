package org.tree.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.tree.entity.Tree;
import org.tree.entity.TreeNode;


/**
 * 抽象树节点服务层
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
public abstract class AbstractTreeNodeService implements TreeNodeService {
	
	public StringBuffer getHTML(Tree tree, Map<String, Object> parameters) {
		StringBuffer html = new StringBuffer();
		List<TreeNode> nodes = getTreeNodes(tree, parameters);
		if (nodes == null || nodes.size() == 0) {
			return null;
		}
		html.append("<ul>");
		for (int i = 0; nodes != null && i < nodes.size(); i++) {
			TreeNode treeNode = nodes.get(i);

			// htmlsrc
			html.append("<li");
			
			if (this.isLeaf(tree,treeNode)) {// 叶子
				if(i == nodes.size() -1){//last
					this.prepareAttribute(html, "class","last");
				}
			} else {// 父节点
				if(i == nodes.size() -1){//last
					this.prepareAttribute(html, "class","closed lastclosed");
				}else{
					this.prepareAttribute(html, "class","closed");
				}
				this.prepareAttribute(html, TreeService.TREE_PARENTID_VAR,tree.getId());
			}

			// params
			Map<String, Object> allParam = new HashMap<String, Object>();
			if (parameters != null) {
				allParam.putAll(parameters);
			}
			Map<String, String> treeNodeParameters = treeNode.getParameters();
			if (treeNodeParameters != null) {
				allParam.putAll(treeNodeParameters);
			}
			allParam.remove(TreeService.TREE_ID_VAR);
			allParam.remove(TreeService.TREE_PARENTID_VAR);
			prepareParameters(html, allParam);

			// url
			if (treeNode.getUrl() != null) {
				this.prepareAttribute(html, "url", treeNode.getUrl());
			}

			// otherAttributes
			Map<String, String> attributes = treeNode.getAttributes();
			if (attributes != null) {
				Iterator<String> attributeKeys = treeNode.getAttributes()
						.keySet().iterator();
				while (attributeKeys.hasNext()) {
					String att = attributeKeys.next();
					prepareAttribute(html, att, treeNode.getAttributes().get(
							att));
				}
			}
			// end otherAttributes
			
			// end <li>
			html.append(" >");
			
			// span
			if (this.isLeaf(tree,treeNode)) {
				html.append("<span class='file'>");
			}else{
				if(i == nodes.size() -1){//last folder
					html.append("<div class='hit closed-hit lastclosed-hit' onclick='$att(this);'></div>");
				}else{
					html.append("<div class='hit closed-hit' onclick='$att(this);'></div>");
				}
				html.append("<span class='folder' onclick='$att(this);'>");
			}
			// a
			html.append("<a href='javascript:void(0);' onclick='$atc(this)'>");
			html.append(StringEscapeUtils.escapeHtml(treeNode.getText()));
			html.append("</a>");
			html.append("</span>");
			//end  </li>
			html.append("</li>");

		}
		html.append("</ul>");
		//System.out.println(html);
		return html;
	}

	/**
	 * 获取子节点
	 * @param parent 树
	 * @param parameters 参数
	 * @return 子节点
	 */
	protected abstract List<TreeNode> getTreeNodes(Tree parent,Map<String, Object> parameters);

	/**
	 * 处理参数
	 * @param handlers html
	 * @param params 参数
	 */
	protected void prepareParameters(StringBuffer handlers,Map<String, Object> params) {
		if (params == null)
			return;
		
		StringBuffer result = new StringBuffer();
	    Iterator<String> iterKeys = params.keySet().iterator();
	    int count =0;
	    while(iterKeys.hasNext()){
	      if(count >0)result.append("&amp;");
	      count ++;
	      
	      String key = iterKeys.next();
	      Object value =params.get(key);
	      if(value != null){
	    	  if(value instanceof String){
	    		  result.append(key).append("=").append(value.toString());
	    	  }else if(value instanceof String[]){
	    		  String[] arrs = (String[])value;
	    		  for (String string : arrs) {
	    			  if(string == null){
	    				  result.append(key).append("=");
	    			  }else{
	    				  result.append(key).append("=").append(string);
	    			  }
	    		  }
	    	  }else{
	    		  throw new RuntimeException("Not support parameter: "+ key +"="+value);//TODO
	    	  }
	      }else{
	    	  result.append(key).append("=");
	      }
	      
	    }
		if(result.length()>0){
			this.prepareAttribute(handlers, "param", result.toString());
		}
	}

	/**
	 * Prepares an attribute if the value is not null, appending it to the the
	 * given StringBuffer.
	 * 
	 * @param handlers
	 *            The StringBuffer that output will be appended to.
	 * @param name 属性名称
	 * @param value 属性值
	 */
	protected void prepareAttribute(StringBuffer handlers, String name,
			Object value) {
		if (value != null) {
			handlers.append(" ");
			handlers.append(name);
			handlers.append("=\"");
			handlers.append(value);
			handlers.append("\"");
		}
	}
	
	/**
	 * @param tree 树
	 * @param treeNode 节点
	 * @return 是否是叶子节点
	 */
	protected boolean isLeaf(Tree tree,TreeNode treeNode){
		return treeNode.isLeaf() || tree.isLeaf();
	}
}
