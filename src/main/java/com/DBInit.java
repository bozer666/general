package com;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

/**
 * Created by 'ms.x' on 2017/8/9.
 */
@Component
public class DBInit implements InitializingBean{
	
	@Autowired
	private Properties properties;
	
	public DatabaseMetaData dbMetaData = null;
	public Connection conn = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (dbMetaData == null) {
			try {
				Class.forName(properties.getDriverClass());
				conn = DriverManager.getConnection(properties.getUrl(),
						properties.getUsername(), properties.getPassword());
				dbMetaData =  conn.getMetaData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
