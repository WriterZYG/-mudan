package util;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * 发送邮件（未使用）
 *
 */
public class MailSend {

    private  String personalName = "micheal";
    private  Session session;
    private Transport transport;
    public void init(String host,String username,String password) throws Exception
    {
    	Properties props = new Properties(); // 获取系统环境
        Authenticator auth =  new SimpleAuthenticator(username, password); // 进行邮件服务器用户认证
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
         
        session = Session.getDefaultInstance(props,auth);
        // 设置session,和邮件服务器进行通讯。
 
        transport = session.getTransport("smtp");   
        System.out.println(host+" "+username+" "+password);
        transport.connect(host, username, password);
        System.out.println("mail init success");
    }
    /**
     * 用来进行服务器对用户的认证
     */
     public class SimpleAuthenticator extends Authenticator {
        
        private String username;        
        private String password;        
        public SimpleAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }
    
        public PasswordAuthentication getPasswordAuthentication() {    
            return new PasswordAuthentication(this.username, this.password);   
        }
     }
    public void sendMail(String mail_subject,String mail_body) throws Exception
    {
    	CommonParam common=new CommonParam();
		common.init("mail.properties");
		init(common.getHost(),common.getUsername(),common.getPassword());
    	 MimeMessage message = new MimeMessage(session);
    	 try {
			message.setSubject(mail_subject);  // 设置邮件标题
         message.setText(mail_body); // 设置邮件正文
         message.setSentDate(new Date()); // 设置邮件发送日期
         Address address;
         String mailto[]=common.getMail_to();
         InternetAddress[] toAddress = new InternetAddress[common.getMail_to().length];
		 address = new InternetAddress(common.getMail_from(), personalName);
         message.setFrom(address); // 设置邮件发送者的地址
         for(int i=0;i<toAddress.length;i++)               //设置接收方地址
         {
           toAddress[i] = new InternetAddress(mailto[i]);
//           System.out.println(mail_to[i]);

         }       
         message.addRecipients(Message.RecipientType.TO, toAddress);
         Transport.send(message); // 发送邮件
         System.out.println("send ok!");
    	 } catch (Exception ex)
         {
             ex.printStackTrace();
             throw new Exception(ex.getMessage());
         }
    }   
        
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MailSend sendmail = new MailSend();
	        try
	        {
	        	sendmail.sendMail("测试文件","你好");
	        } catch (Exception ex)
	        {
	        }
	}

}

