package org.svnadmin.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.svnadmin.dao.DbInitDao;

/**
 * 数据库初始化
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * @since 3.0.7
 */
@Service("DbInitService")
public class DbInitService implements InitializingBean {
	
	@Resource(name = DbInitDao.BEAN_NAME)
	protected DbInitDao dbInitDao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
	}
	
	
	private void init() {
		try {
			this.dbInitDao.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
