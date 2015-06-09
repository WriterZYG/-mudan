package snooway.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 与本体数据库的连接池
 * @author Bys
 *
 */
public class DBConnectionPool_ontology {
	 private static   DBConnectionPool_ontology instance;  
	    private  ComboPooledDataSource dataSource;  
	  
	    private DBConnectionPool_ontology(String drv, String url, String user, String pass) throws SQLException, PropertyVetoException {  
	        dataSource = new ComboPooledDataSource();  
	  
	        dataSource.setUser(user);  
	        dataSource.setPassword(pass);  
	        dataSource.setJdbcUrl(url);  
	        dataSource.setDriverClass(drv);  
	        //自定义的配置  最小连接 1 个，最大连接 10 个
	        dataSource.setInitialPoolSize(1); 
	        dataSource.setMinPoolSize(1);  
	        dataSource.setMaxPoolSize(10);  
	        dataSource.setMaxStatements(0);  
	        dataSource.setMaxIdleTime(60);
	        dataSource.setIdleConnectionTestPeriod(60);
//	        dataSource.setDebugUnreturnedConnectionStackTraces(true);
	    } 
	  
	    public static final DBConnectionPool_ontology getInstance(String drv, String url, String user, String pass) {  
	        if (instance == null) {  
	            try {  
	                instance = new DBConnectionPool_ontology(drv, url, user, pass);  
	            } catch (Exception e) {  
	                e.printStackTrace();
	            }  
	        }
	        return instance;  
	    }  

	    public synchronized final Connection getConnection() {  
	        Connection conn = null;
	        try {
	            conn = dataSource.getConnection();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }
	        return conn;
	    }
}
