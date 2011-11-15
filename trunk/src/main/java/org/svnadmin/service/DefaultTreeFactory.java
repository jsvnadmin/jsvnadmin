/**
 * 
 */
package org.svnadmin.service;

import java.util.ArrayList;
import java.util.List;

import org.svnadmin.util.SpringUtils;
import org.tree.entity.Tree;
import org.tree.service.TreeFactory;
import org.tree.service.TreeNodeService;

//TODO 提供JdbcTreeFactory，把树配置数据保存在数据库。

/**
 * 默认的树工厂类
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
public class DefaultTreeFactory implements TreeFactory {

	/**
	 * 单例
	 */
	private static DefaultTreeFactory instance = new DefaultTreeFactory();
	
	/**
	 * 
	 */
	private DefaultTreeFactory(){
		
	}
	
	/**
	 * @return the instance
	 */
	public static DefaultTreeFactory getInstance() {
		return instance;
	}
	
	public TreeNodeService findTreeNodeService(Tree tree) {
		return SpringUtils.getBean(tree.getTreeNodeService());
	}
	
	public Tree find(String id) {
		for(Tree  tree:datas){
			if(tree.getId().equals(id)){
				//要给leaf设值
				if(tree.getId().equals(tree.getParentId())){
					tree.setLeaf(false);
				}else{
					tree.setLeaf(findChildren(tree.getId()).size()==0);
				}
				return tree;
			}
		}
		return null;
	}
	
	public List<Tree> findChildren(String parentId) {
		List<Tree> results = new ArrayList<Tree>();
		for(Tree  tree:datas){
			if(parentId.equals(tree.getParentId())){
				//要给leaf设值
				if(tree.getId().equals(tree.getParentId())){
					tree.setLeaf(false);
					results.add(tree);
					return results;
				}else{
					tree.setLeaf(findChildren(tree.getId()).size()==0);
					results.add(tree);
				}
			}
		}
		return results;
	}
	
	
	/**
	 * 
	 */
	private static List<Tree> datas = new ArrayList<Tree>();
	static{
		/*
		 * rep
		 *   |rep
		 *   |    |rep 
		 *   |    |rep
		 *   |rep
		 *   |
		 * rep
		 *   |    
		 */
		datas.add(new Tree("rep","rep",RepTreeNodeService.BEAN_NAME));
		/*
		 * com
		 *   |_dept
		 *   |   |_dept user
		 *   |   |_dept user
		 *   |_dept
		 *   |_com user
		 *   |_com user
		 * 
		 */
//		datas.add(new Tree("com",null));
		
//		datas.add(new Tree("dept","com"));
//		datas.add(new Tree("user","com"));
		
//		datas.add(new Tree("user","dept"));
	}

}
