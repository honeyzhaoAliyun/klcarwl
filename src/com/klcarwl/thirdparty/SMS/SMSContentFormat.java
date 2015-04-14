package com.klcarwl.thirdparty.SMS;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSContentFormat {
	private static SimpleDateFormat sdf = new SimpleDateFormat("M月d日hh时m分");
	public static String getRegisterSMS(){
		return "恭喜您注册成功，，欢迎使用快乐物流系统。。 %1。" + sdf.format(new Date());
	}
	
	public static String getFindPwdVerifyCodeSMS(String verifyCode){
		return "尊敬的客户您好，您使用了找回密码功能，验证码为"+verifyCode+",请在快乐物流系统输入该验证码,30分钟内输入有效。" + sdf.format(new Date());
	}
	
	public static String getResetPwdSuccessSMS(){
		return "尊敬的客户您好，您的密码重置成功，欢迎使用快乐物流系统。" + sdf.format(new Date());
	}
	public static String getValidateVerifyCodeSMS(String verifyCode){
		return "尊敬的客户您好，您用来身份认证的验证码为"+verifyCode+",请在快乐物流系统输入该验证码,30分钟内输入有效。" + sdf.format(new Date());
	}
}
