package com.klcarwl.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.klcarwl.model.HavefeeUser;
import com.klcarwl.model.UserActivity;
import com.klcarwl.model.UserInfo;
import com.klcarwl.service.HavefeeUserService;
import com.klcarwl.service.UserActivityService;
import com.klcarwl.service.UserInfoService;

@Controller
public class WechatUserController extends BaseController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserActivityService userActivityService;
	@Autowired
	private HavefeeUserService havefeeUserService;
	//抢话费总额
	private double sumcost =0D;
	//已充值话费总额
	private double sumhavefee =0D;
	
	private UserInfo userInfo;
	
	private UserActivity userActivity;
	
	private HavefeeUser  havefeeUser;
	
	private List<UserInfo> userinfoList;
	
	private List<UserActivity> userActivityList;
	
	private List<HavefeeUser> havefeeUserList;
	
	@ResponseBody
	@RequestMapping(value = ("/wechatuser"), method = RequestMethod.GET)
	public String index(@RequestParam(required = false) String openid,HttpServletRequest request,HttpServletResponse response) {	
		userinfoList = new ArrayList<UserInfo>();
		userActivityList = new ArrayList<UserActivity>();
		havefeeUserList = new ArrayList<HavefeeUser>();
		JSONObject jsonobject = new JSONObject();
		JSONObject wechatuserJson = new JSONObject();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			
		if(openid !=null && !openid.equals("")){
			userinfoList = userInfoService.getList("wechatKey", openid);
			if(userinfoList.size() > 0){
				userInfo = new UserInfo();
				userInfo = userinfoList.get(0);
				jsonobject.accumulate("name", userInfo.getName());
				jsonobject.accumulate("wechatkey", userInfo.getWechatKey());
				jsonobject.accumulate("nickname", userInfo.getNickName());
				jsonobject.accumulate("mobile", userInfo.getMobile());
				
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
		return wechatuserJson.toString();
	}

	public double getSumcost() {
		return sumcost;
	}

	public void setSumcost(double sumcost) {
		this.sumcost = sumcost;
	}

	public double getSumhavefee() {
		return sumhavefee;
	}

	public void setSumhavefee(double sumhavefee) {
		this.sumhavefee = sumhavefee;
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

	public HavefeeUser getHavefeeUser() {
		return havefeeUser;
	}

	public void setHavefeeUser(HavefeeUser havefeeUser) {
		this.havefeeUser = havefeeUser;
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

	public List<HavefeeUser> getHavefeeUserList() {
		return havefeeUserList;
	}

	public void setHavefeeUserList(List<HavefeeUser> havefeeUserList) {
		this.havefeeUserList = havefeeUserList;
	}
	
}
