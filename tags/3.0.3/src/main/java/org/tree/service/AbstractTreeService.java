package org.tree.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tree.entity.Tree;

/**
 * 抽象树服务层
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
public abstract class AbstractTreeService implements TreeService {

	/**
	 * 日志
	 */
	private static final Log LOG = LogFactory.getLog(AbstractTreeService.class);

	public String getHTML(Map<String, Object> parameters) {
		try {
			String treeId = (String) parameters.get(TREE_ID_VAR);
			String parentId = (String) parameters.get(TREE_PARENTID_VAR);

			if (StringUtils.isBlank(treeId) && StringUtils.isBlank(parentId)) {
				return null;
			}

			StringBuffer html = new StringBuffer();

			if (StringUtils.isNotBlank(parentId)) {
				// 找出所有的子树
				List<Tree> treeList = getTreeFactory().findChildren(parentId);
				for (Tree tree : treeList) {
					if (tree == null) {
						continue;
					}
					parseTree(html, tree, parameters);
				}
			} else if (StringUtils.isNotBlank(treeId)) {
				// 说明是第一层
				Tree tree = getTreeFactory().find(treeId);
				if (tree == null) {
					LOG.info("not found tree. id = " + treeId);
					return null;
				}
				parseTree(html, tree, parameters);
			}
			return html.toString();

			// LOG.info(html.toString());
		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param treeHtml html
	 * @param tree 树
	 * @param parameters 参数
	 */
	protected void parseTree(StringBuffer treeHtml, Tree tree,
			Map<String, Object> parameters) {
		StringBuffer html;
		try {
			html = getTreeFactory().findTreeNodeService(tree).getHTML(tree,
					parameters);
		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
			html = null;
		} finally {
		}

		if (html == null) {
			LOG.debug("not found tree html data." + tree);
			return;
		}
		treeHtml.append(html);
	}

}
