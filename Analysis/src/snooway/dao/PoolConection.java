package snooway.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * 数据库连接
 * @author Nie Yu
 *
 */
public class PoolConection {

	protected Connection cn = null;
	protected Statement stm = null;
	protected PreparedStatement pstm;
	
	protected boolean prep;
	
	/**
	 * 
	 */
	protected int parameterIndex;
	
	private  String drv,url,username,password;
	
	
	public String getDrv() {
		return drv;
	}

	public void setDrv(String drv) {
		this.drv = drv;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCn(Connection cn) {
		this.cn = cn;
	}

	public void setStm(Statement stm) {
		this.stm = stm;
	}

	public void setPstm(PreparedStatement pstm) {
		this.pstm = pstm;
	}

	public void setPrep(boolean prep) {
		this.prep = prep;
	}

	public void setParameterIndex(int parameterIndex) {
		this.parameterIndex = parameterIndex;
	}

	public PoolConection(String drv,String url,String username,String password,int flag) throws SQLException {
//		System.out.println("enter new PoolConection");
		this.drv=drv;
		this.url=url;
		this.username=username;
		this.password=password;
		if(flag==1)
		{
		cn = getConn();}
		else if(flag==3){
		cn=getConn_app2();
		}else if(flag == 0)
		{
			cn = getConn_server();
		}
		else{
			cn = getConn_ontology();
		}
//		System.out.println("cn has got,set transaction and stm");
		cn.setAutoCommit(true);
		stm = cn.createStatement();
//		System.out.println("new PoolConection complete");
	}
	
	public boolean transaction_start(){
		try {
			cn.setAutoCommit(false);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void transaction_commit() throws SQLException{
		cn.commit();
		cn.setAutoCommit(true);
	}
	
	public void rollback() throws SQLException{
		cn.rollback();
		cn.setAutoCommit(true);
	}
	
	
	/**
	 * 添加数据库连接池的处理模式
	 * @return 
	 */
	protected Connection getConn() {
		DBConnectionPool pool = DBConnectionPool.getInstance(drv, url, username, password);
		Connection conn = pool.getConnection();
		/*try {
			Class.forName(drv).newInstance();
			conn=DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return conn;
	}
	protected Connection getConn_app2() {
		DBConnectionPool_app2 pool = DBConnectionPool_app2.getInstance(drv, url, username, password);
		Connection conn = pool.getConnection();
		/*try {
			Class.forName(drv).newInstance();
			conn=DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return conn;
	}
	protected Connection getConn_ontology() {
		DBConnectionPool_ontology pool = DBConnectionPool_ontology.getInstance(drv, url, username, password);
		Connection conn = pool.getConnection();
		/*try {
			Class.forName(drv).newInstance();
			conn=DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return conn;
	}
	
	protected Connection getConn_server() {
//		System.out.println("enter getConn_server");
		DBConnectionPool_server pool = DBConnectionPool_server.getInstance(drv, url, username, password);
//		System.out.println("DBConnectionPool_server.getInstance:"+pool+" has been got");
		Connection conn = pool.getConnection();
//		System.out.println("conn"+conn+" has been got");
		/*try {
			Class.forName(drv).newInstance();
			conn=DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return conn;
	}
	public void setAutoCommit(boolean autoCommit) throws SQLException{
		cn.setAutoCommit(autoCommit);
	}
	
	public void commit() throws SQLException{
		cn.commit();
	}

	/**
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		
		System.out.println("executeQuery: " + sql);
		return stm.executeQuery(sql);
		
	}

	/**
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public boolean execute(String sql) throws SQLException {
		System.out.println("execute: " + sql);
		return stm.execute(sql);
	}
	
	/**
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void prepareSql(String sql) throws SQLException{
		System.out.println("prepareSql: " + sql);
		prep = true;
		parameterIndex = 1;
		pstm = cn.prepareStatement(sql);
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean execute() throws SQLException{
		if(prep){
			prep = false;
			parameterIndex = 0;
			return pstm.execute();
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery() throws SQLException{
		if(prep){
			prep = false;
			parameterIndex = 0;
			return pstm.executeQuery();
		}
		return null;
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void setString(int parameterIndex, String x) throws SQLException{
		if(prep){
			pstm.setString(parameterIndex, x);
		}
	}
	
	/**
	 *
	 * @throws SQLException
	 */
	public void setInt(int parameterIndex, int x) throws SQLException{
		if(prep){
			pstm.setInt(parameterIndex, x);
		}
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void setBoolean(int parameterIndex, boolean x) throws SQLException{
		if(prep){
			pstm.setBoolean(parameterIndex, x);
		}
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void setDate(int parameterIndex, long time) throws SQLException{
		if(prep){
			pstm.setTimestamp(parameterIndex, new Timestamp(time));
		}
	}

	public void setDate(int parameterIndex, java.util.Date date) throws SQLException{
		if(prep){
			if (date==null)
				pstm.setTimestamp(parameterIndex, null);
			else
				pstm.setTimestamp(parameterIndex, new Timestamp(date.getTime()));
		}
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void setString(String x) throws SQLException{
		if(prep){
			pstm.setString(parameterIndex++, x);
		}
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void setInt(int x) throws SQLException{
		if(prep){
			pstm.setInt(parameterIndex++, x);
		}
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void setBoolean(boolean x) throws SQLException{
		if(prep){
			pstm.setBoolean(parameterIndex++, x);
		}
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void setDate(long time) throws SQLException{
		if(prep){
			pstm.setTimestamp(parameterIndex++, new Timestamp(time));
		}
	}

	public void setDate(java.util.Date date) throws SQLException{
		if(prep){
			if (date==null)
				pstm.setTimestamp(parameterIndex++, null);
			else{
				pstm.setTimestamp(parameterIndex++, new Timestamp(date.getTime()));
			}
		}
	}
	
	public void close() {
		try {
			
			if (stm != null) {
				stm.close();
			}
			if (cn != null) {
				cn.close();
			}

		} catch (SQLException ex) {
		}
	}

	public Connection getCn() {
		return cn;
	}



	public PreparedStatement getPstm() {
		return pstm;
	}

	public boolean isPrep() {
		return prep;
	}
	
	public Statement getStm(){
		return stm;
	}

	/**
	 *
	 * @return parameterIndex
	 */
	public int getParameterIndex() {
		return parameterIndex;
	}
}
