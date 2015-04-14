package com.klcarwl.thirdparty.SMS;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class BaiwutongSMS {
	private static String smsUserName = "2e4h001";
	private static String smsUserPwd = "zdkj";
	
	//注册成功时，发送短信
	public static boolean RegisterSuccess(String mobile, String smsId) throws Exception{
		return sendSMS(mobile, BizCode.REGISTER_SUCCESS, SMSContentFormat.getRegisterSMS(), smsId);
	}
	
	//找回密码时，发送验证码
	public static boolean findPwdVerifyCode(String mobile, String smsId, String verifyCode) throws Exception{
		return sendSMS(mobile, BizCode.FIND_PASSWORD_VERIFY_CODE, SMSContentFormat.getFindPwdVerifyCodeSMS(verifyCode), smsId);
	}
	
	//
	public static boolean resetPwdSuccess(String mobile, String smsId) throws Exception{
		return sendSMS(mobile, BizCode.RESET_PASSWORD_SUCCESS, SMSContentFormat.getResetPwdSuccessSMS(), smsId);
	}
	//身份认证
	public static boolean validateVerifyCode(String mobile, String smsId, String verifyCode) throws Exception{
		return sendSMS(mobile, BizCode.VALIDATE_VERIFY_CODE, SMSContentFormat.getValidateVerifyCodeSMS(verifyCode), smsId);
	}
	/**
	 * @author honey.zhao@aliyun.com
	 * ========================
	 *  账号：2e4h001
	 *  密码：zdkj
	 *	业务代码：
	 *	10655093yd（签名快乐大车）
	 *	1069yd（签名快乐物流）
	 *-------------------------
	 *  账号：2ecj001
	 * 	密码：ecj001
	 * 	业务代码：1069yd
	 * 	签名：【快乐优油】
	 * ========================
	 * @param mobile
	 * @param bizCode
	 * @param smsContent
	 * @param smsId
	 * @return
	 * @throws Exception
	 */
	public static boolean sendSMS(String mobile, String bizCode, String smsContent, String smsId) throws Exception {
		
		boolean result = false;
		
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://service2.baiwutong.com:8080/sms_send2.do");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
		NameValuePair[] data = { new NameValuePair("corp_id", smsUserName), 
				new NameValuePair("corp_pwd", smsUserPwd), 
				new NameValuePair("corp_service", "1069yd"), 
				new NameValuePair("mobile", mobile),
				new NameValuePair("msg_content", smsContent), 
				new NameValuePair("corp_msg_id ", smsId), 
				new NameValuePair("ext ", getExtCodeByBiz(bizCode)) };
		post.setRequestBody(data);
		client.executeMethod(post);
		
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		
		if("200".equals(statusCode)){
			result = true;
		}
		
		for (Header h : headers) {
			System.out.println(h.toString());
		}
		String resultText = new String(post.getResponseBodyAsString().getBytes("gbk"));
		
		System.out.println(resultText);
		post.releaseConnection();
		
		return result;
	}
	
	//根据业务代码获取小号
	public static String getExtCodeByBiz(String bizCode){
		return bizCode;
	}
	
	public static void main(String[] args){
		try {
			BaiwutongSMS.validateVerifyCode("18511859606", "1", "843232");//联通
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}