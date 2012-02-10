package org.svnadmin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.svnadmin.entity.Ajax;
import org.svnadmin.service.AjaxService;
import org.svnadmin.util.SpringUtils;


/**
 * ajax调用入口<br>
 * 例子:
 * ajaxTreeService.ajax
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 */
public class AjaxServlet  extends BaseServlet{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 4811876317189957164L;

	/**
	 * 日志
	 */
	@SuppressWarnings("hiding")
	private static final Log LOG = LogFactory.getLog(AjaxServlet.class);
	
	/**
	 * 默认的ajax调用content type
	 */
	private static final String DEFAULT_CONTENTTYPE = "text/html; charset=UTF-8";
	
	/**
	 * 后缀
	 */
	public static final String AJAX = ".ajax";

    @Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter out=null;
    	try {
    		this.validate(request);
            
    		String uri = request.getRequestURI();
    		if(!uri.endsWith(AJAX)){
    			return;
    		}
    		String serviceName = "";
    		int lastSlash = uri.lastIndexOf("/");
    		if(lastSlash==-1){
    			serviceName = uri.substring(0, uri.length()-AJAX.length());
    		}else{
    			serviceName = uri.substring(lastSlash+1, uri.length()-AJAX.length());
    		}
            
            if (StringUtils.isBlank(serviceName)) {
            	LOG.warn("service name is null.");
            	return;
            }
            
            AjaxService service = SpringUtils.getBean(serviceName);
            if(service==null){
            	throw new ServletException("not found service. name = "+serviceName);
            }
            
            Map<String,Object> parameters = this.getRequestParameters(request);
            
            Ajax ajax=service.execute(parameters);
            
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "No-cache");
            response.setDateHeader("Expires", 1);
            if(ajax!=null){
            	if(ajax.getContentType()!=null && !"".equals(ajax.getContentType())){
            		response.setContentType(ajax.getContentType());
            	}else{
            		response.setContentType(DEFAULT_CONTENTTYPE);
            	}
            	if(ajax.getResult()!=null && !"".equals(ajax.getResult())){
	            	out = response.getWriter();
	            	out.println(ajax.getResult());
            	}
            }
            
            //LOG.info(result);
        } catch (Exception e) {
            LOG.error(e);
        	e.printStackTrace();
        } finally {
            if (out != null) {
            	out.flush();
                out.close();
            }
        }
    }
    
	/**
	 * @param request 请求
	 * @return 参数
	 */
	@SuppressWarnings("rawtypes")
	protected Map<String,Object> getRequestParameters(HttpServletRequest request){
    	Enumeration names = request.getParameterNames();
    	if(names!=null){
    		Map<String,Object> results = new HashMap<String,Object>();
    		while(names.hasMoreElements()){
    			String name = (String)names.nextElement();
    			String[] values = request.getParameterValues(name);
    			if(values == null){
    				results.put(name, null);
    			}else if(values.length==1){
    				results.put(name, values[0]);//request.getParameter(name)
    			}else{
    				results.put(name, values);
    			}
    		}
    		return results;
    	}
    	return null;
    }
    
}
