package org.svnadmin.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository(DbInitDao.BEAN_NAME)
public class DbInitDao extends Dao {
	
	private final Log LOG = LogFactory.getLog(DbInitDao.class);
	
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME="dbInitDao";
	
	private boolean check() {
		String sql = "select count(1) from i18n";
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = this.getConnection();
			stmt = conn.createStatement();
			stmt.executeQuery(sql);
			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			this.close(null,stmt, conn);
		}
	}
	
	public void init() {
		if (this.check()) {
			return;
		}
		this.execute("create table i18n	(lang varchar(20) not null,id varchar(200) not null,lbl varchar(200) not null,primary key (lang, id))");
		this.execute("create table pj (pj varchar(50) not null, path varchar(200) not null,url varchar(200) not null,type varchar(10) not null,des varchar(200),primary key (pj))");
		this.execute("create table pj_gr (pj varchar(50) not null, gr varchar(50) not null, des varchar(100), primary key (pj, gr) )");
		this.execute("create table pj_gr_auth (pj varchar(50) not null, gr varchar(50) not null, res varchar(200) not null, rw varchar(10), primary key (pj, res, gr))");
		this.execute("create table pj_gr_usr ( pj varchar(50) not null, gr varchar(50) not null, usr varchar(50) not null, primary key (pj, usr, gr) )");
		this.execute("create table pj_usr( pj varchar(50) not null, usr varchar(50) not null, psw varchar(50) not null, primary key (usr, pj) )");
		this.execute("create table pj_usr_auth( pj varchar(50) not null, usr varchar(50) not null, res varchar(200) not null, rw varchar(10), primary key (pj, res, usr) )");
		this.execute("create table usr(usr varchar(50) not null, psw varchar(50) not null, name varchar(50), role varchar(10), primary key (usr) )");
		
		this.execute("alter table pj_gr add constraint FK_Relationship_2 foreign key (pj) references pj (pj)");
		this.execute("alter table pj_gr_auth add constraint FK_Reference_6 foreign key (pj, gr) references pj_gr (pj, gr)");
		this.execute("alter table pj_gr_usr add constraint FK_Reference_10 foreign key (pj, gr) references pj_gr (pj, gr)");
		this.execute("alter table pj_gr_usr add constraint FK_Reference_9 foreign key (usr) references usr (usr)");
		this.execute("alter table pj_usr add constraint FK_Reference_5 foreign key (pj) references pj (pj)");
		this.execute("alter table pj_usr add constraint FK_Reference_7 foreign key (usr) references usr (usr)");
		this.execute("alter table pj_usr_auth add constraint FK_Reference_11 foreign key (pj) references pj (pj)");
		this.execute("alter table pj_usr_auth add constraint FK_Reference_8 foreign key (usr) references usr (usr)");
		
		LOG.info("init db ok");
	}
}
