package snooway.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 数据库连接配置
 * @author Bys
 *
 */
public class PoolConnectCfg {
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

	public PoolConnectCfg(String fname) {
		try
		{
			System.out.println("fname = " + fname);
			
			InputStream is = new FileInputStream(fname);
//			if (null == is)
//				return;
			Properties config = new Properties();
			config.load(is);
			System.out.println("is = " + is);
			String value = null;
			if ((value = config.getProperty("jdbc.driverClassName")) != null)
				drv = value;
			
			if ((value = config.getProperty("jdbc.url")) != null)
			{
				url = value;
				System.out.println("jdbc url = " + url);
			}
			
			if ((value = config.getProperty("jdbc.username")) != null)
				username = value;
			
			if ((value = config.getProperty("jdbc.password")) != null)
				password = value;

		
		}
		catch (Exception e)
		{
			e.printStackTrace(System.err);
		}
	}
}
