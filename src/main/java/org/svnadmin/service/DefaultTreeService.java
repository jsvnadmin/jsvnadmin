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
 * @author Harvey
 *
 */
@Service(DefaultTreeService.BEAN_NAME)
public class DefaultTreeService extends AbstractTreeService implements AjaxService{
	
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
