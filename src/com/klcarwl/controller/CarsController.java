package com.klcarwl.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.klcarwl.model.UserInfo;
import com.klcarwl.service.UserInfoService;


@Controller
@RequestMapping(value = "/cars")
public class CarsController extends BaseController {
	@Autowired
	private UserInfoService userInfoService;
	
	private UserInfo userInfo;
	
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	
}
