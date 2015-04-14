package com.klcarwl.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.klcarwl.model.ValidateLog;
import com.klcarwl.thirdparty.SMS.BaiwutongSMS;
import com.klcarwl.util.EMAILScheduler;
import com.klcarwl.util.RandomNumberUtil;

@Repository
public class ValidateLogDaoImpl extends BaseDaoImpl<ValidateLog, Long> implements ValidateLogDao {

	/**
	 * 生成一个验证码，发送给用户的邮箱或者手机号，同时将其存储
	 * @param type		发送类型，1邮箱验证码，2手机号验证码
	 * @param sendTo	发送的目的地
	 */
	public void saveAndSendValidateMsg(String type, String sendTo,String ids) {
		// TODO Auto-generated method stub
		Long random=RandomNumberUtil.randomLong(6);		
		if("1".equals(type)){
			//发送到邮件	
			StringBuffer sendMsg=new StringBuffer("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>邮箱验证</title><style type=\"text/css\" >html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, details, embed, figure, figcaption, footer, header, hgroup, menu, nav, output, ruby, section, summary, time, mark, audio, video {margin: 0;padding: 0;border: 0;font-size: 100%;vertical-align: baseline;}article, aside, details, figcaption, figure, footer, header, hgroup, menu, nav, section {display: block;}ol, ul {list-style: none;}blockquote, q {quotes: none;}blockquote:before, blockquote:after, q:before, q:after {content: '';content: none;}table {border-collapse: collapse;border-spacing: 0;}img {border: none;vertical-align: middle;-ms-interpolation-mode: bicubic;}ul, ol, li, dl, dd, dt {list-style: none;}a:link {text-decoration: none;}a:hover {text-decoration: none;}a:active {text-decoration: none;}a:visited {text-decoration: none;}body {margin: 0px;padding: 0px;font-family: \"微软雅黑\";color: #585858;font-family: Arial, Helvetica, sans-serif;line-height: 36px;}.clear {clear: both;}.boxTemplate {width: 1024px;margin: 0px auto 0 20px;display: block;background: #fff;}.top {width: 1024px;height: 80px;margin: 10px auto;display: block;}.content {width: 1024px;height: 400px;margin-top: 20px;display: block;}.call {width: 1024px;color: #000;line-height: 36px;}.call h1 {font-size: 18px;}.email {color: #0000ff;text-decoration: underline}.email1 {color: #0000ff;}.btn {width: 1024px;height: 90px;margin: 0 auto;display:block;}.btn_yz {width:305px;height: 60px;background: url(http://image.klcar.com/klcar/images/yz.png) no-repeat;margin: 25px 200px;}.btn_yz a{ cursor:pointer;}.yz{width: 180px;color: #fff;font: normal 24px/36px \"微软雅黑\";line-height:30px;margin:5px auto 10px 40px;padding:10px;letter-spacing:4px;display:block;}.btn a {color: #FFF;margin-top: 10px;}.footer {width: 1024px;float: left;display: block;margin:0 auto;text-align: right;line-height:16px;}.tip{width: 120px;margin: 20px 100px auto 650px;float: left;display: block;text-align: center;}</style></head>");
			sendMsg.append("<body><div class=\"boxTemplate\"><div class=\"top\"><img src=\"http://image.klcar.com/klcar/images/YouYunlogo.png\"/></div><div class=\"content\"><div class=\"call\"><h1>尊敬的会员<a href=\"#\"><span class=\"email\">");
			sendMsg.append(sendTo);
			sendMsg.append("</span></a>，您好!</h1>感谢您对快乐物流<a href=\"http://oil.klcar.com\"><span class=\"email1\">（oil.klcar.com）</span></a>的支持，我们会为您提供最贴心的服务！您的身份认证链接：");
			sendMsg.append("<a href=\"http://oil.klcar.com/YouYun365/login/_resetpasswordinput?ids="+ids+" \"> <span class=\"email1\">http://oil.klcar.com/YouYun365/login/_resetpasswordinput?ids="+ids+"</span></a>");
			sendMsg.append("<br />（这是一封自动产生的email，请勿回复。）</div><div class=\"footer\"><div class=\"tip\"><h2>快乐大车-快乐物流</h2><br /> <a href=\"http://oil.klcar.com/ \"> <span class=\"email1\">oil.klcar.com</span></a></div></div></div></div></body></html>");
			new EMAILScheduler(sendTo, "身份认证信息",sendMsg.toString()).start();
		}
		if("2".equals(type)){
			//发送手机号		
			try {
				BaiwutongSMS.validateVerifyCode(sendTo, "1", random.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ValidateLog validateLog=getValidateLogBySendTo(sendTo);
		//如果不存在数据创建，存在修改
		if(validateLog==null){
			validateLog=new ValidateLog();
			validateLog.setType(type);
			validateLog.setIsUse("1");
			validateLog.setSendTo(sendTo);
			validateLog.setValidateCode(random.toString());
			validateLog.setCreateDate(new Date());
			validateLog.setModifyDate(new Date());
			getSession().save(validateLog);
		}else{
			validateLog.setCreateDate(new Date());
			validateLog.setModifyDate(new Date());
			validateLog.setType(type);
			validateLog.setIsUse("1");
			validateLog.setValidateCode(random.toString());
			getSession().update(validateLog);
		}
	}
	/**
	 * 验证验证码信息是否正确
	 * @param type
	 * @param code
	 * @return
	 */
	public boolean validate(String type,String code, String sendTo) {
		// TODO Auto-generated method stub
		boolean result=false;
		ValidateLog validateLog=getValidateLogBySendTo(sendTo);
		if(validateLog!=null){
			result=false;
			//如果验证码类型不一致验证失败
			if(type==null||(!type.equals(validateLog.getType()))){
				result=false;
			}else{
				//当时发送时间
				Date createDate=validateLog.getCreateDate();
				//当前时间
				Date theDate=new Date();
				//3分钟后验证码验证失败
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(createDate);
				gc.add(Calendar.MINUTE, 3);
				Date theOutCreateDate=gc.getTime();
				if(theDate.before(theOutCreateDate)&&code!=null&&code.equals(validateLog.getValidateCode())){
					result=true;
				}
			}
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ValidateLog getValidateLogBySendTo(String sendTo) {
		List paramt=new ArrayList();

		StringBuffer hql=new StringBuffer("FROM ValidateLog vl WHERE vl.sendTo=? and vl.isUse =? order by id desc ");
		paramt.add(sendTo);
		paramt.add("1");
		List list = this.queryForList(hql.toString(), paramt.toArray());
		return (ValidateLog) (list.isEmpty() ? null : list.get(0));
	}
	
}
