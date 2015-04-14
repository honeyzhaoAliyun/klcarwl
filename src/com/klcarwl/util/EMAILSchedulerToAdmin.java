package com.klcarwl.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.log4j.Logger;

/**
 * 
 * Title: klcar Platform
 * 
 * Author:  zhaoguoqing  
 *
 * Date: 2014-6-22
 * 
 * Description: E-MAIL发送公共类
 * 
 */
public class EMAILSchedulerToAdmin extends Thread{
	
	private static Logger logger = Logger.getLogger(EMAILSchedulerToAdmin.class);
	
	// 邮件发送者地址
	private static final String SENDER_EMAIL_ADDR = "noreply@ccompass.com.cn";

	// 邮件发送者邮箱用户
	private static final String SMTP_USER_NAME = "noreply@ccompass.com.cn";

	// 邮件发送者邮箱密码
	private static final String SMTP_PASSWORD = "123456ccom";

	// 邮件发送者邮箱SMTP服务器
	private static final String SMTP_SERVER_NAME = "smtp.mxhichina.com";

	// 传输类型
	private static final String TRANSPORT_TYPE = "smtp";

	// 属性
	private static Properties props;
	
	static {
		props = new Properties();
		
		// 存储发送邮件服务器的信息
		props.put("mail.smtp.host", SMTP_SERVER_NAME);

		props.put("mail.smtp.port", "25");
		
		props.put("mail.smtp.auth", "true");
	}

	private String email;
	private String title;
	private String content;
	
	public EMAILSchedulerToAdmin(String email, String title, String content) {
		this.email = email;
		this.title = title;
		this.content = content;
	}
	
	@Override
	public void run() {
		logger.info("Start asyn execute send E-MAIL");
		
		props = new Properties();
		
		props.put("mail.smtp.host", SMTP_SERVER_NAME);

		props.put("mail.smtp.port", "25");
		
		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getInstance(props, null);

		// 设置调试标志,要查看经过邮件服务器邮件命令，可以用该方法
		session.setDebug(true);
		
		// 由邮件会话新建一个消息对象
		MimeMessage message = new MimeMessage(session);
		
		Transport transport = null;

		try {
			// 设置发件人
			Address from = new InternetAddress(SENDER_EMAIL_ADDR, "中斗优油");
			message.setFrom(from);

			// 设置收件人
			Address to = new InternetAddress(email);
			
			message.setRecipient(RecipientType.TO, to);

			// 设置主题
			message.setSubject(title);
			
			//指定邮箱内容及ContentType和编码方式
			message.setContent(getContent(content), "text/html;charset=utf-8");
			
			// 设置发信时间
			message.setSentDate(new Date());
			
			// 存储邮件信息
			message.saveChanges();
			
			transport = session.getTransport(TRANSPORT_TYPE);
			
			// 要填入你的用户名和密码；
			transport.connect(SMTP_SERVER_NAME, SMTP_USER_NAME, SMTP_PASSWORD);

			// 发送邮件,其中第二个参数是所有已设好的收件人地址
			transport.sendMessage(message, message.getAllRecipients());

			logger.info("邮件发送成功!");

		} catch (Exception e) {
			logger.error("Send mail fail!\n" + e.getMessage());
		} finally {
			if (null != transport){
				try {
					transport.close();
				} catch (MessagingException e) {
					logger.error("Send mail fail!\n" + e.getMessage());
				}
			}
		}
	}
	
	private String getContent(String content){
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
		sb.append("<style>");
		sb.append("body{ padding:0; margin:0;}");
		sb.append(".mail{ width:784px; margin:0 auto;}");
		sb.append(".top{ width:784px; height:271px; background:url(===) no-repeat;}");
		sb.append(".content{ width:784px; min-height:348px; height:348px; background:url(==) center bottom repeat-x; padding-top:20px;}");
		sb.append(".content td{ font-family:'宋体'; font-size:14px; color:#000000; padding:0 15px; line-height:24px;}");
		sb.append(".footer{ width:784px; height:3s6px; line-height:36px; background:url(==) repeat-x; text-align:center; font-family:'宋体'; font-size:12px; color:#ffffff;}");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<div class='mail'>");
		sb.append("<div class='top'></div>");
		sb.append("<table class='content'>");
		sb.append(" <tr><td align='left' valign='top' >");
		sb.append(content);
		sb.append("</td></tr>");
		sb.append("</table>");
		sb.append("</div></body></html>");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		StringBuffer sendMsg=new StringBuffer("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>邮箱验证</title><style type=\"text/css\" >html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, details, embed, figure, figcaption, footer, header, hgroup, menu, nav, output, ruby, section, summary, time, mark, audio, video {margin: 0;padding: 0;border: 0;font-size: 100%;vertical-align: baseline;}article, aside, details, figcaption, figure, footer, header, hgroup, menu, nav, section {display: block;}ol, ul {list-style: none;}blockquote, q {quotes: none;}blockquote:before, blockquote:after, q:before, q:after {content: '';content: none;}table {border-collapse: collapse;border-spacing: 0;}img {border: none;vertical-align: middle;-ms-interpolation-mode: bicubic;}ul, ol, li, dl, dd, dt {list-style: none;}a:link {text-decoration: none;}a:hover {text-decoration: none;}a:active {text-decoration: none;}a:visited {text-decoration: none;}body {margin: 0px;padding: 0px;font-family: \"微软雅黑\";color: #585858;font-family: Arial, Helvetica, sans-serif;line-height: 36px;}.clear {clear: both;}.boxTemplate {width: 1024px;margin: 0px auto 0 20px;display: block;background: #fff;}.top {width: 1024px;height: 80px;margin: 10px auto;display: block;}.content {width: 1024px;height: 400px;margin-top: 20px;display: block;}.call {width: 1024px;color: #000;line-height: 36px;}.call h1 {font-size: 18px;}.email {color: #0000ff;text-decoration: underline}.email1 {color: #0000ff;}.btn {width: 1024px;height: 90px;margin: 0 auto;display:block;}.btn_yz {width:305px;height: 60px;background: url(http://image.klcar.com/klcar/images/yz.png) no-repeat;margin: 25px 200px;}.btn_yz a{ cursor:pointer;}.yz{width: 180px;color: #fff;font: normal 24px/36px \"微软雅黑\";line-height:30px;margin:5px auto 10px 40px;padding:10px;letter-spacing:4px;display:block;}.btn a {color: #FFF;margin-top: 10px;}.footer {width: 1024px;float: left;display: block;margin:0 auto;text-align: right;line-height:16px;}.tip{width: 120px;margin: 20px 100px auto 650px;float: left;display: block;text-align: center;}</style></head>");
		sendMsg.append("<body><div class=\"boxTemplate\"><div class=\"top\"><img src=\"http://image.klcar.com/klcar/images/logo.png\"/></div><div class=\"content\"><div class=\"call\"><h1>尊敬的会员<a href=\"#\"><span class=\"email\">shenhaixiang@ccompass.com.cn</span></a>，您好!</h1>感谢您注册快乐大车<a href=\"http://www.klcar.com/klcar/shop/user_info!index.action\"><span class=\"email1\">（www.klcar.com）</span></a>，我们将为您提供最贴心的服务！点击以下按钮，即可完成邮箱安全验证： </div><div class=\"btn\"><div class=\"btn_yz\"><a href=\"http://www.klcar.com/klcar/shop/user_info!index.action\"><span class=\"yz\">开始验证邮箱</span></a></div></div><div class=\"call\">您也可以点击以下链接，完成邮箱安全验证：<br /> <a href=\"http://www.klcar.com:80/klcar/shop/user_info!validateEmail.action?userId=5843&code=867935 \"> <span class=\"email\">http://www.klcar.com:80/klcar/shop/user_info!validateEmail.action?userId=5843&code=867935 </span></a><br />如果您无法直接点击链接地址，请获取地址，拷贝到浏览器地址栏访问。（这是一封自动产生的email，请勿回复。）</div><div class=\"footer\"><div class=\"tip\"><h2>快乐大车</h2><br /> <a href=\"http://www.klcar.com/klcar/shop/user_info!index.action\"> <span class=\"email1\">www.klcar.com</span></a></div></div></div></div></body></html>");
		
		new EMAILSchedulerToAdmin("zfshi@126.com", "ss",sendMsg.toString()).start();
	}
}
