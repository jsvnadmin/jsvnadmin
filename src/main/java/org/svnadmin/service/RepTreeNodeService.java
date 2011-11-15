/**
 * 
 */
package org.svnadmin.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.svnadmin.Constants;
import org.svnadmin.dao.PjDao;
import org.svnadmin.dao.PjUsrDao;
import org.svnadmin.entity.Pj;
import org.svnadmin.entity.PjUsr;
import org.svnadmin.entity.Usr;
import org.svnadmin.util.EncryptUtil;
import org.svnadmin.util.I18N;
import org.svnadmin.util.UsrProvider;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tree.entity.Tree;
import org.tree.entity.TreeNode;
import org.tree.service.AbstractTreeNodeService;

/**
 * 仓库目录结构树节点服务层
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
@Service(RepTreeNodeService.BEAN_NAME)
public class RepTreeNodeService extends AbstractTreeNodeService {
	
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME="repTreeNodeService";
	/**
	 * 日志
	 */
	private final Logger LOG = Logger.getLogger(RepTreeNodeService.class);
	
	
	/**
	 * 项目DAO
	 */
	@Resource(name=PjDao.BEAN_NAME)
	PjDao pjDao;
	/**
	 * 项目用户DAO
	 */
	@Resource(name=PjUsrDao.BEAN_NAME)
	PjUsrDao pjUsrDao;
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<TreeNode> getTreeNodes(Tree parent,
			Map<String, Object> parameters) {
		List<TreeNode> results = new ArrayList<TreeNode>();
		
		String pjId = (String) parameters.get("pj");
		String path = (String) parameters.get("path");
		if(StringUtils.isBlank(pjId)){
			LOG.warn("pj id is blank ");
			return null;
		}
		if(StringUtils.isBlank(path)){
			path = "/";//root
		}
		
		Pj pj = pjDao.get(pjId);
		if(pj == null){
			LOG.warn("Not found project: "+pjId);
			return null;
		}
		
		Usr usr = UsrProvider.getCurrentUsr();
		
		String svnUrl = pj.getUrl();
		String svnUserName = usr.getUsr();
		String svnPassword = usr.getPsw();
		if(!Constants.HTTP_MUTIL.equals(pj.getType())){
			//pj_usr覆盖用户的密码
			PjUsr pjUsr = pjUsrDao.get(pjId, svnUserName);
			if(pjUsr != null){
				svnPassword = pjUsr.getPsw();
			}
		}
		svnPassword = EncryptUtil.decrypt(svnPassword);//解密
		
    	try{
	    	 SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(svnUrl));
    	     ISVNAuthenticationManager authManager = 
    	                  SVNWCUtil.createDefaultAuthenticationManager(svnUserName, svnPassword);
    	     repository.setAuthenticationManager(authManager);
	
    	    
//	    	System.out.println("项目的根路径： "+repository.getRepositoryRoot(true));
	    	
	    	
	    	 SVNProperties properties = new SVNProperties();
	    	 Collection<SVNDirEntry> entries = repository.getDir(path, SVNRevision.HEAD.getNumber(), properties, (Collection) null);
//	    	 Collection<SVNDirEntry> entries = repository.getDir("/trunk", SVNRevision.HEAD.getNumber(), properties, (Collection) null);
	    	 for (SVNDirEntry svnDirEntry : entries) {
//				System.out.println(svnDirEntry);
//	    		System.out.println(svnDirEntry.getName()//文件夹或文件名
//	    				+","+svnDirEntry.getKind()//类型,参考SVNNodeKind.FILE,SVNNodeKind.DIR
//	    				+","+svnDirEntry.getRevision()//版本
//	    				+","+svnDirEntry.getAuthor()//作者
//	    				+","+svnDirEntry.getSize()//如果kind是SVNNodeKind.FILE时返回文件的大小
//	    				+","+svnDirEntry.getDate());//日期
	    		 TreeNode treeNode = new TreeNode(svnDirEntry.getName());
	    		 treeNode.setLeaf(SVNNodeKind.FILE.equals(svnDirEntry.getKind()));//叶子?
	    		 treeNode.addParamete("pj", pjId);
	    		 if(path.endsWith("/")){
	    			 treeNode.addParamete("path", path+svnDirEntry.getName());
	    		 }else{
	    			 treeNode.addParamete("path", path+"/"+svnDirEntry.getName());
	    		 }
	    		 results.add(treeNode);
			}
	    	 Collections.sort(results);//排序
    	}catch(SVNAuthenticationException e){
    		LOG.error(e.getMessage());
//			e.printStackTrace();
			
    		results.clear();
    		TreeNode errorNode = new TreeNode(I18N.getLbl("svn.auth.error", "没有权限"));
    		errorNode.setLeaf(true);
    		results.add(errorNode);
    		return results;
    	}catch (SVNException e) {
    		LOG.error(e.getMessage());
//			e.printStackTrace();
			
    		results.clear();
    		TreeNode errorNode = new TreeNode(e.getMessage());
    		errorNode.setLeaf(true);
    		results.add(errorNode);
    		return results;
		}
		
    	return results;
	}
	
	static {
        /*
         * For using over http:// and https://
         */
        DAVRepositoryFactory.setup();
        /*
         * For using over svn:// and svn+xxx://
         */
        SVNRepositoryFactoryImpl.setup();
        
        /*
         * For using over file:///
         */
        FSRepositoryFactory.setup();
    }

}
