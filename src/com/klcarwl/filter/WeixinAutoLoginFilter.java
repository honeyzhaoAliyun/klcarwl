package com.klcarwl.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.klcarwl.model.UserInfo;
import com.klcarwl.util.ConstantUtil;
import com.klcarwl.util.JDBCDataSource;
import com.klcarwl.util.RestUtil;

/**
 * 
 * Title: klcarwl Platform
 * 
 * Author: zhaoguoqing
 * 
 * Date: 2015-1-13
 * 
 * Description:微信自动登录过滤
 *
 */
public class WeixinAutoLoginFilter implements Filter {

	private UserInfo memberUser = null;
	static Connection conn2 = null;
	static PreparedStatement pstm2 = null;
	static ResultSet rs2 = null;
	
	private String oauthUrl ;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


 		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		
		String path = httpRequest.getContextPath();
		String basePath = httpRequest.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		StringBuffer sqlbuffer = new StringBuffer();
		if(httpRequest.getMethod().equals("GET")){
			/**
			 * @author honey.zhao@aliyun.com
			 *  被坑了半天 后来才明白
			 *  HttpServletRequest 的这两种方法都只能得到不包含参数的请求url，区别如下：
			 *		1   前者返回相对路径，后者返回完整路径
	  		 *		2 前者返回string ，后者返回stringbuffer
			 *		要想得到完整请求url可以通过如下方法，getQueryString()得到的是url后面的参数串，和前者相加就是带参数的请求路径了
			 *
	    	 *		String queryString = request.getQueryString();
			 *		String fullPath = url + queryString;   // 或者是url_buffer.toString()+queryString;
			 */
			String queryString = httpRequest.getQueryString();
			//TODO 此处没有用getRequestURL()是后边授权地址重定向 [*项目的可配置性*]
			StringBuffer url  = new StringBuffer();
			url.append(httpRequest.getRequestURI());
			if(queryString !=null){
				url.append("?"+queryString);
			}
			
			Object openid = session.getAttribute("openid");
			if(openid == null){
				//---------授权后将获取到code--------------------
				String code = httpRequest.getParameter("code");
				if (code != null && !"".equals(code)) {
					//----------------获取openid-------begin-----
					String oAuthCode = RestUtil.oAuthCodegetInvoke("code", code);
					JSONObject result = JSONObject.fromObject(oAuthCode);
					JSONObject accessTokenOAuth = JSONObject.fromObject(result.get("accessTokenOAuth"));
					openid = accessTokenOAuth.getString("openid");
					/**______session openid_______*/
					session.setAttribute("openid", openid);
					//----------------获取openid-------end-------
					conn2 = JDBCDataSource.getConnection();
					sqlbuffer.append("SELECT user_info.ID,user_info.NAME,user_info.SEX,user_info.USER_NAME,user_info.NICK_NAME,user_info.MOBILE,user_info.PASSWORD,user_info.STATUS,user_info.EMAIL,user_info.IS_USE,user_info.WECHAT_KEY,user_info.CREATE_DATE,user_info.MODIFY_DATE,user_info.IP FROM USER_INFO ");
					sqlbuffer.append("where user_info.WECHAT_KEY='"+openid+"'");	
					try {
						pstm2 = conn2.prepareStatement(sqlbuffer.toString());
						rs2 = pstm2.executeQuery();
						while (rs2.next()) {
							memberUser = new UserInfo();
							memberUser.setUserName(rs2.getString("USER_NAME")==null?"":rs2.getString("USER_NAME"));
						}
						if(memberUser !=null){
							httpResponse.sendRedirect(httpRequest.getRequestURI());
							return;
						}
						memberUser = null;
					} catch (SQLException e4) {
						e4.printStackTrace();
					} finally {
						JDBCDataSource.closeAll(rs2, pstm2, conn2);
					}
					//将code存在session 中
					session.setAttribute("code", code);
					
				}else{
					/**
					 * oauth授权
					 */
					//--------判断是否是活动页面 ：相应的判断是否弹出授权页面-----------
					if(url.toString().contains("activity")){
						oauthUrl = RestUtil.oAuthgetInvoke("uri", ConstantUtil.get("WULIU_IP")+url.toString(),"snsapi_userinfo");
					}else{
						oauthUrl = RestUtil.oAuthgetInvoke("uri", ConstantUtil.get("WULIU_IP")+url.toString(),"snsapi_base");
					}
					
					httpResponse.sendRedirect(oauthUrl);
					return;
				}
			}
			
			/**
			 * @author honey.zhao@aliyun.com
			 * @Date  2015-04-13
			 * eg.
			 * 1)因为accesstonken 和 jsApi_ticket 每天的请求次数有限 经过 WechatSDK 
			 * 增加access_token和jsapi_ticket监听每7000秒刷新一次 保存在 properties文件中
			 * 2)WechatSDK 中添加获取access_token和jsapi_ticket 的REST接口 便于物流商城klcarwl调用[ (~ .~)嘻嘻 面向资源架构嘛 ROA 哥也小拽下名词]
			 */
			String jsApiticket = RestUtil.jsApiticketInvoke(null, null);
			JSONObject jsApiticketResult = JSONObject.fromObject(jsApiticket);
			JSONObject jsApiticketJson = JSONObject.fromObject(jsApiticketResult.get("jsapitoken"));
			
			session.setAttribute("appId", jsApiticketJson.getString("appId"));
			session.setAttribute("appSecret", jsApiticketJson.getString("appSecret"));
			session.setAttribute("token", jsApiticketJson.getString("token"));
			session.setAttribute("jsapi_ticket", jsApiticketJson.getString("jsapi_ticket"));
		}
		chain.doFilter(httpRequest, httpResponse);
	}

	public void init(FilterConfig config) throws ServletException {

	}

	public static Connection getConn2() {
		return conn2;
	}

	public static void setConn2(Connection conn2) {
		WeixinAutoLoginFilter.conn2 = conn2;
	}

	public static PreparedStatement getPstm2() {
		return pstm2;
	}

	public static void setPstm2(PreparedStatement pstm2) {
		WeixinAutoLoginFilter.pstm2 = pstm2;
	}

	public static ResultSet getRs2() {
		return rs2;
	}

	public static void setRs2(ResultSet rs2) {
		WeixinAutoLoginFilter.rs2 = rs2;
	}

	public UserInfo getMemberUser() {
		return memberUser;
	}

	public void setMemberUser(UserInfo memberUser) {
		this.memberUser = memberUser;
	}

	public String getOauthUrl() {
		return oauthUrl;
	}

	public void setOauthUrl(String oauthUrl) {
		this.oauthUrl = oauthUrl;
	}
	

}
