package br.com.rest.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.klcarwl.model.UserActivity;
import com.klcarwl.model.UserInfo;
import com.klcarwl.service.UserActivityService;
import com.klcarwl.service.UserActivityServiceImpl;
import com.klcarwl.service.UserInfoService;
import com.klcarwl.service.UserInfoServiceImpl;

import net.sf.json.JSONObject;

@WebServlet(urlPatterns = {"/wechatuser/*"})
public class WechatUserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UserInfo userInfo;
	
	private UserActivity userActivity;
	
	private List<UserInfo> userinfoList;
	
	private List<UserActivity> userActivityList;
	
	private double sumcost =0D;

	@SuppressWarnings("rawtypes")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		userinfoList = new ArrayList<UserInfo>();
		userActivityList = new ArrayList<UserActivity>();
		resp.setCharacterEncoding("UTF-8");
		JSONObject jsonobject = new JSONObject();
		JSONObject wechatuserJson = new JSONObject();
		String openid = req.getParameter("openid");
		if(openid !=null && !openid.equals("")){
			UserInfoService userInfoService = new UserInfoServiceImpl();
			userinfoList = userInfoService.getList("wechatKey", openid);
			if(userinfoList.size() > 0){
				userInfo = new UserInfo();
				userInfo = userinfoList.get(0);
				jsonobject.accumulate("name", userInfo.getName());
				jsonobject.accumulate("wechatkey", userInfo.getWechatKey());
				jsonobject.accumulate("nickname", userInfo.getNickName());
				
				UserActivityService userActivityService = new UserActivityServiceImpl();
				userActivityList = userActivityService.getList("parentUserInfo", userInfo);
				//计算话费总和
				for(Iterator iter =userActivityList.iterator();iter.hasNext();){
					userActivity = new UserActivity();
					userActivity = (UserActivity) iter.next();
					sumcost = sumcost +userActivity.getHelpCost();
				}
				jsonobject.accumulate("sumcost", sumcost);
			}
			wechatuserJson.accumulate("wechatuser", jsonobject);
		}
		PrintWriter out = resp.getWriter();
	    out.write(wechatuserJson.toString());
	}

	public List<UserInfo> getUserinfoList() {
		return userinfoList;
	}

	public void setUserinfoList(List<UserInfo> userinfoList) {
		this.userinfoList = userinfoList;
	}

	public List<UserActivity> getUserActivityList() {
		return userActivityList;
	}

	public void setUserActivityList(List<UserActivity> userActivityList) {
		this.userActivityList = userActivityList;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserActivity getUserActivity() {
		return userActivity;
	}

	public void setUserActivity(UserActivity userActivity) {
		this.userActivity = userActivity;
	}

	public double getSumcost() {
		return sumcost;
	}

	public void setSumcost(double sumcost) {
		this.sumcost = sumcost;
	}

}
