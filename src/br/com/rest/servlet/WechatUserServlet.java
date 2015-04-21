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

import com.klcarwl.model.HavefeeUser;
import com.klcarwl.model.UserActivity;
import com.klcarwl.model.UserInfo;
import com.klcarwl.service.HavefeeUserService;
import com.klcarwl.service.HavefeeUserServiceImpl;
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
	
	private HavefeeUser  havefeeUser;
	
	private List<UserInfo> userinfoList;
	
	private List<UserActivity> userActivityList;
	
	private List<HavefeeUser> havefeeUserList;
	
	//抢话费总额
	private double sumcost =0D;
	//已充值话费总额
	private double sumhavefee =0D;

	@SuppressWarnings("rawtypes")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		userinfoList = new ArrayList<UserInfo>();
		userActivityList = new ArrayList<UserActivity>();
		havefeeUserList = new ArrayList<HavefeeUser>();
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
				jsonobject.accumulate("mobile", userInfo.getMobile());
				
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
			//获取已充话费信息
			HavefeeUserService havefeeUserService = new HavefeeUserServiceImpl();
			havefeeUserList = havefeeUserService.getList("openid", openid);
			if(havefeeUserList.size() > 0){
				//计算已充话费总和
				for(Iterator iter =havefeeUserList.iterator();iter.hasNext();){
					havefeeUser = new HavefeeUser();
					havefeeUser = (HavefeeUser) iter.next();
					sumhavefee = sumhavefee +havefeeUser.getTopupCost();
				}
				jsonobject.accumulate("sumhavefee", sumhavefee);
			}
			//============封装jsonObject==============================
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

	public HavefeeUser getHavefeeUser() {
		return havefeeUser;
	}

	public void setHavefeeUser(HavefeeUser havefeeUser) {
		this.havefeeUser = havefeeUser;
	}

	public List<HavefeeUser> getHavefeeUserList() {
		return havefeeUserList;
	}

	public void setHavefeeUserList(List<HavefeeUser> havefeeUserList) {
		this.havefeeUserList = havefeeUserList;
	}

	public double getSumhavefee() {
		return sumhavefee;
	}

	public void setSumhavefee(double sumhavefee) {
		this.sumhavefee = sumhavefee;
	}

}
