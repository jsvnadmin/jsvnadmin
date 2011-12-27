/**
 * 
 */
package org.svnadmin.service;

import java.io.File;
import java.util.Collection;

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

/**
 * 仓库服务层
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.2
 * 
 */
@Service(RepositoryService.BEAN_NAME)
public class RepositoryService{
	
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME="repositoryService";
	/**
	 * 日志
	 */
	private final Logger LOG = Logger.getLogger(RepositoryService.class);
	
	
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
	
	/**
	 * 获取svn仓库
	 * @param pjId 项目ID
	 * @return svn仓库
	 * @throws SVNException svn异常，例如没有权限等
	 */
	public SVNRepository getRepository(String pjId) throws SVNException{
		Pj pj = pjDao.get(pjId);
		if(pj == null){
			LOG.warn("Not found project: "+pjId);
			return null;
		}
		return this.getRepository(pj);
	}
	
	/**
	 * 从项目的url中获取svn的url
	 * @param url 项目url
	 * @return svn url
	 */
	public static String parseURL(String url){
		if(StringUtils.isBlank(url)){
			return null;
		}
		String result = url.trim();//去空格
		result = StringUtils.replace(result, "\t", " ");
		result = StringUtils.replace(result, "\r", " ");
		result = StringUtils.replace(result, "\n", " ");
		result = StringUtils.replace(result, "\b", " ");
		result = StringUtils.replace(result, "<", " ");//eg. <br/>
		result = StringUtils.replace(result, "(", " ");//eg. ()
		
		result = result.trim();
		int blank = result.indexOf(" ");
		if(blank != -1){
			result = result.substring(0, blank);
		}
		
		return result;
	}
	
	/**
	 * 获取svn仓库
	 * @param pj 项目
	 * @return svn仓库
	 * @throws SVNException svn异常，例如没有权限等
	 */
	public SVNRepository getRepository(Pj pj) throws SVNException{
		
		Usr usr = UsrProvider.getCurrentUsr();
		
		String svnUrl = parseURL(pj.getUrl());
		if(StringUtils.isBlank(svnUrl)){
			throw new RuntimeException(I18N.getLbl("pj.error.url", "URL不可以为空"));
		}
		String svnUserName = usr.getUsr();
		String svnPassword = usr.getPsw();
		if(!Constants.HTTP_MUTIL.equals(pj.getType())){
			//pj_usr覆盖用户的密码
			PjUsr pjUsr = pjUsrDao.get(pj.getPj(), svnUserName);
			if(pjUsr != null){
				svnPassword = pjUsr.getPsw();
			}
		}
		svnPassword = EncryptUtil.decrypt(svnPassword);//解密
		
    	 SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIDecoded(svnUrl));
	     ISVNAuthenticationManager authManager = 
	                  SVNWCUtil.createDefaultAuthenticationManager(svnUserName, svnPassword);
	     repository.setAuthenticationManager(authManager);
	     
	     return repository;
	}
	
	/**
	 * 返回项目仓库的根
	 * @param pj 项目
	 * @return 仓库根
	 */
	public String getRepositoryRoot(Pj pj){
		SVNRepository repository = null;
		try{
			repository = this.getRepository(pj);
			return repository.getRepositoryRoot(true).toString();
		}catch(SVNAuthenticationException e){
    		LOG.error(e.getMessage());
    		return null;
    	} catch (SVNException e) {
    		LOG.error(e.getMessage());
    		e.printStackTrace();
			return null;
		}finally{
			if(repository != null){
				repository.closeSession();
			}
		}
	}
	
	
	/**
	 * 获取项目指定路径的svn仓库文件系统
	 * @param pj 项目
	 * @param path 相对仓库根目录的路径
	 * @return 目录或文件系统
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection<SVNDirEntry> getDir(String pj,String path){
		if(StringUtils.isBlank(path)){
			path = "/";//root
		}
		if(!path.startsWith("/")){
			path = "/"+path;
		}
		SVNRepository repository = null;
		try {
			repository = this.getRepository(pj);
			SVNProperties properties = new SVNProperties();
	    	return repository.getDir(path, SVNRevision.HEAD.getNumber(), properties, (Collection) null);
    	}catch(SVNAuthenticationException e){
    		e.printStackTrace();
			throw new RuntimeException(I18N.getLbl("svn.auth.error", "认证失败"));
    	}catch (SVNException e) {
    		e.printStackTrace();
    		throw new RuntimeException(e.getMessage());
		}finally{
			if(repository!=null){
				repository.closeSession();
			}
		}
	}

	/**
     * Creates a local blank FSFS-type repository.
     * A call to this routine is equivalent to 
     * <code>createLocalRepository(path, null, enableRevisionProperties, force)</code>.
     * 
     * @param  respository                          a repository root location
     * @return                               a local URL (file:///) of a newly
     *                                       created repository
     */
	public static SVNURL createLocalRepository(File respository){
		try {
			return SVNRepositoryFactory.createLocalRepository(respository, true,
					false);
		} catch (SVNException e) {
			throw new RuntimeException(I18N.getLbl("pj.save.error.createRepository","创建仓库失败.{0}",new Object[]{respository.getAbsolutePath()}) 
			+ " : "+ e.getMessage());
		}
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
