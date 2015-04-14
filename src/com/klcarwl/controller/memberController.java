package com.klcarwl.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.klcarwl.model.SysLocale;
import com.klcarwl.model.UserInfo;
import com.klcarwl.service.MsgContentService;
import com.klcarwl.service.SysLocaleService;
import com.klcarwl.service.UserInfoService;
import com.klcarwl.vo.MsgContentVo;


@Controller
@RequestMapping(value = "/member")
public class memberController extends BaseController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private MsgContentService msgContentService;
	@Autowired
	private SysLocaleService sysLocaleService;
	
	private UserInfo userInfo;
	
	List<Map> msgContentVoList;
	private List<Map> msgContentVoNewList;
	
	@RequestMapping(value = ("/_register1"), method = RequestMethod.GET)
	public ModelAndView _register1(@RequestParam("email") String email,HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		mv.addObject("email", email);
		mv.setViewName("rest_word");
		return mv;
	}
	
	@RequestMapping(value = ("/publishGood"), method = RequestMethod.GET)
	public ModelAndView publishGood(HttpServletRequest request,HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		if(request.getSession().getAttribute("sessionInfo") == null){
			mv.setViewName("redirect:/loginpage.do");
		}else{
			mv.setViewName("publish_good");
		}
		return mv;
	}
	@RequestMapping(value = ("/publishCar"), method = RequestMethod.GET)
	public ModelAndView publishCar(HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		mv.setViewName("publish_car");
		return mv;
	}
	@RequestMapping(value = ("/searchGoods"), method = RequestMethod.GET)
	public ModelAndView searchGoods(HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		mv.setViewName("search_goods");
		return mv;
	}
	@RequestMapping(value = ("/myGoods"), method = RequestMethod.GET)
	public ModelAndView myGoods(MsgContentVo msgContentVo,HttpServletRequest request,HttpServletResponse response) {	
		userInfo=(UserInfo)request.getSession().getAttribute("sessionInfo");
		msgContentVoList = new ArrayList<Map>();
		msgContentVoNewList = new ArrayList<Map>();
		ModelAndView mv=new ModelAndView();
		if(request.getSession().getAttribute("sessionInfo") == null){
			mv.setViewName("redirect:/loginpage.do");
		}else{
			msgContentVoList = msgContentService.findMsgContentsByUser(userInfo);
			if(msgContentVoList.size() > 0){
				for(Map<String, Object> objMap : msgContentVoList){
					objMap.put("FromName", this.getOneCityById(objMap.get("FROM_LOCALE").toString()).getName());
					objMap.put("ToName", this.getOneCityById(objMap.get("TO_LOCALE").toString()).getName());
					msgContentVoNewList.add(objMap);
				}
			}
			mv.addObject("msgContentVoList", msgContentVoNewList);
			mv.setViewName("my_goods");
		}
		return mv;
	}
	
	@RequestMapping(value = ("/myAccount"), method = RequestMethod.GET)
	public ModelAndView myAccount(HttpServletRequest request,HttpServletResponse response) {	
		userInfo=(UserInfo)request.getSession().getAttribute("sessionInfo");
		ModelAndView mv=new ModelAndView();
	   
	    mv.addObject("userInfo", userInfo);
	    mv.setViewName("myAccount");
		return mv;
	}
	
	@RequestMapping(value = ("/searchCars"), method = RequestMethod.GET)
	public ModelAndView searchCars(HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		mv.setViewName("search_cars");
		return mv;
	}
	@RequestMapping(value = ("/memberCenter"), method = RequestMethod.GET)
	public ModelAndView memberCenter(HttpServletRequest request) {	
		ModelAndView mv=new ModelAndView();
		if(request.getSession().getAttribute("sessionInfo") == null){
			mv.setViewName("redirect:/loginpage.do");
		}else{
			mv.setViewName("member_center");
		}
		return mv;
	}
	@RequestMapping(value = ("/updatePwd"), method = RequestMethod.GET)
	public ModelAndView updatePwd(HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		mv.setViewName("member_update_pwd");
		return mv;
	}
	@RequestMapping(value = ("/updatePwd"), method = RequestMethod.POST)
	public ModelAndView updatePwdPost(@RequestParam(required = true) String oldPwd, @RequestParam(required = true) String newPwd, @RequestParam(required = true) String newPwd2,
            HttpServletRequest request) {	
		ModelAndView mv=new ModelAndView();
		mv.setViewName("member_update_pwd");
		if(oldPwd==null||"".equals(oldPwd)){
			mv.addObject("errorMsg", "旧密码不能为空！");	
			return mv;
		}
		UserInfo userInfo=(UserInfo)request.getSession().getAttribute("sessionInfo");
		if(!DigestUtils.md5Hex(oldPwd).equals(userInfo.getPassword())){
			mv.addObject("errorMsg", "旧密码输入错误！");	
			return mv;
		}
		if(newPwd==null||"".equals(newPwd)){
			mv.addObject("errorMsg", "新密码不能为空！");	
			return mv;
		}
		if(newPwd.length()<6||newPwd.length()>16){
			mv.addObject("errorMsg", "密码长度为6到16位之间!");	
			return mv;
		}
		if(!newPwd.equals(newPwd2)){
			mv.addObject("errorMsg", "两次新密码输入不一致!");	
			return mv;
		}
		UserInfo userInfo1=userInfoService.get(userInfo.getId());
		userInfo1.setPassword(DigestUtils.md5Hex(newPwd));
		userInfoService.update(userInfo1);
		mv.addObject("errorMsg", "密码修改成功!");	
		return mv;
	}
	
	@RequestMapping(value = ("/updateComInfo"), method = RequestMethod.GET)
	public ModelAndView updateInfo(HttpServletRequest request) {	
		userInfo=(UserInfo)request.getSession().getAttribute("sessionInfo");
		ModelAndView mv=new ModelAndView();
		UserInfo userInfo1=userInfoService.get(userInfo.getId());
		mv.setViewName("member_com_edit");
		mv.addObject("userInfo", userInfo1);
		return mv;
	}
	@RequestMapping(value = ("/updateCarInfo"), method = RequestMethod.GET)
	public ModelAndView updateCarInfo(HttpServletRequest request) {	
		userInfo=(UserInfo)request.getSession().getAttribute("sessionInfo");
		ModelAndView mv=new ModelAndView();
		UserInfo userInfo1=userInfoService.get(userInfo.getId());
		mv.addObject("userInfo", userInfo1);
		
		mv.setViewName("member_car_edit");
		return mv;
	}
	
	@RequestMapping(value = ("/updateComInfo"), method = RequestMethod.POST)
	public ModelAndView updateInfoPost(UserInfo userInfoMsg,HttpServletRequest request) {	
		userInfo=(UserInfo)request.getSession().getAttribute("sessionInfo");
		ModelAndView mv=new ModelAndView();
		UserInfo userInfo1=userInfoService.get(userInfo.getId());
		userInfo1.setCompanyName(userInfoMsg.getCompanyName());
		userInfo1.setCity(userInfoMsg.getCity());
		userInfo1.setAddress(userInfoMsg.getAddress());
		userInfo1.setAbs(userInfoMsg.getAbs());
		userInfo1.setComLinkName(userInfoMsg.getComLinkName());
		userInfo1.setComLinkPhone(userInfoMsg.getComLinkPhone());
		userInfo1.setComQQ(userInfoMsg.getComQQ());
		userInfo1.setComMobile(userInfoMsg.getComMobile());
		userInfoService.update(userInfo1);
		mv.setViewName("member_com_edit");
		mv.addObject("userInfo", userInfo1);
		mv.addObject("errorMsg", "资料修改成功!");
		return mv;
	}
	@RequestMapping(value = ("/updateCarInfo"), method = RequestMethod.POST)
	public ModelAndView updateCarInfoPost(UserInfo userInfoMsg,HttpServletRequest request) {	
		userInfo=(UserInfo)request.getSession().getAttribute("sessionInfo");
		ModelAndView mv=new ModelAndView();
		
		UserInfo userInfo1=userInfoService.get(userInfo.getId());
		userInfo1.setPlateNo(userInfo.getPlateNo());
		userInfo1.setLength(userInfo.getLength());
		userInfo1.setLoad(userInfo.getLoad());
		userInfo1.setModels(userInfo.getModels());
		userInfo1.setName(userInfo.getName());
		userInfo1.setPhone(userInfo.getPhone());
		userInfo1.setMobile(userInfo.getMobile());
		userInfoService.update(userInfo1);
		mv.addObject("userInfo", userInfo1);
		mv.setViewName("member_car_edit");
		mv.addObject("errorMsg", "资料修改成功!");
		return mv;
	}
	public SysLocale getOneCityById(String id){
		SysLocale sysLocale = sysLocaleService.get("id", Long.parseLong(id));
		return sysLocale;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public List<Map> getMsgContentVoList() {
		return msgContentVoList;
	}
	public void setMsgContentVoList(List<Map> msgContentVoList) {
		this.msgContentVoList = msgContentVoList;
	}
	public List<Map> getMsgContentVoNewList() {
		return msgContentVoNewList;
	}
	public void setMsgContentVoNewList(List<Map> msgContentVoNewList) {
		this.msgContentVoNewList = msgContentVoNewList;
	}
	
	
}
