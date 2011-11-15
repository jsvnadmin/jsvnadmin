/**
 * 
 */
package org.svnadmin.service;

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
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
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
	 * 获取svn仓库
	 * @param pj 项目
	 * @return svn仓库
	 * @throws SVNException svn异常，例如没有权限等
	 */
	public SVNRepository getRepository(Pj pj) throws SVNException{
		
		Usr usr = UsrProvider.getCurrentUsr();
		
		String svnUrl = pj.getUrl();
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
			return repository .getRepositoryRoot(true).toString();
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
