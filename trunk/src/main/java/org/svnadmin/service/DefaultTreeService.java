/**
 * 
 */
package org.svnadmin.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.svnadmin.entity.Ajax;
import org.tree.service.AbstractTreeService;
import org.tree.service.TreeFactory;

/**
 * 默认的树服务层
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
@Service(DefaultTreeService.BEAN_NAME)
public class DefaultTreeService extends AbstractTreeService implements AjaxService{
	
	/**
	 * Bean名称s
	 */
	public static final String BEAN_NAME="ajaxTreeService";
	
	public TreeFactory getTreeFactory() {
		return DefaultTreeFactory.getInstance();
	}

	public Ajax execute(Map<String, Object> parameters) {
		Ajax result = new Ajax();
		result.setContentType(CONTENTTYPE_HTML);
		result.setResult(this.getHTML(parameters));
		return result;
	}

}
