package util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
 
/**
 * 发送邮件用的一些参数（未使用）
 * @author syq
 */
public class CommonParam {
    
	private static String host;
    // 这个是你的邮箱用户名
    private static String username;
    // 你的邮箱密码
    private static String password;
    private static String mail_to;
    private static String mail_from ;
    public static void init(String resourceFilePath)  {
    	InputStream is;
		try {
			is = new FileInputStream(resourceFilePath);
		
		if (null == is)
			return;
		Properties config = new Properties();
		config.load(is);
//		System.out.println("is = " + is);
		String value = null;
		if ((value = config.getProperty("host")) != null)
			host = value;
		    System.out.println("host = " + host);
		
		if ((value = config.getProperty("username")) != null)
		{
			username = value;
			System.out.println("username = " + username);
		}
		if ((value = config.getProperty("password")) != null)
		{
			password = value;
			System.out.println("password = " + password);
		}
		
		if ((value = config.getProperty("mail_to")) != null)
			mail_to = value;
		
		if ((value = config.getProperty("mail_from")) != null)
			mail_from = value;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
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
	public String[] getMail_to() {
		String[] mail_toes=mail_to.split(",");
		return mail_toes;
	}
	public void setMail_to(String mail_to) {
		this.mail_to = mail_to;
	}
	public String getMail_from() {
		return mail_from;
	}
	public void setMail_from(String mail_from) {
		this.mail_from = mail_from;
	}

}