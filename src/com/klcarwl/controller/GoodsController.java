package com.klcarwl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.klcarwl.model.MsgContent;
import com.klcarwl.model.SysLocale;
import com.klcarwl.model.UserInfo;
import com.klcarwl.service.MsgContentService;
import com.klcarwl.service.SysLocaleService;
import com.klcarwl.util.DateTimeHelper;
import com.klcarwl.util.JsonMsg;
import com.klcarwl.vo.MsgContentVo;


@Controller
@RequestMapping(value = "/goods")
public class GoodsController extends BaseController {
	
	@Autowired
	private MsgContentService msgContentService;
	@Autowired
	private SysLocaleService sysLocaleService;
	
	private UserInfo userInfo;
	private MsgContent msgContent;
	
	private List<Map> msgContentVoList;
	private List<Map> msgContentVoNewList;
	
	@RequestMapping(value = ("/good_edit"), method = RequestMethod.GET)
	public ModelAndView good_edit(@RequestParam(required = false)String goodId,HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		msgContent = new MsgContent();
		if(goodId!=null && !goodId.equals("")){
			msgContent = msgContentService.get(Long.parseLong(goodId));
		}
		if(msgContent.getId() !=null){
			mv.addObject("model", msgContent);
		}
		mv.setViewName("publish_good");
		return mv;
	}
	@RequestMapping(value = ("/good_delete"), method = RequestMethod.GET)
	public ModelAndView good_delete(@RequestParam(required = false)String goodId,HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		msgContent = new MsgContent();
		if(goodId!=null && !goodId.equals("")){
			msgContentService.delete(Long.parseLong(goodId));
		}
		mv.setViewName("redirect:/member/myGoods.do");
		return mv;
	}
	
	@RequestMapping(value = ("/_register1"), method = RequestMethod.GET)
	public ModelAndView _register1(@RequestParam("email") String email,HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		mv.addObject("email", email);
		mv.setViewName("rest_word");
		return mv;
	}
	
	@RequestMapping(value = ("/publishGood"), method = RequestMethod.GET)
	public ModelAndView publishGood(HttpServletRequest request,HttpServletResponse response) {	
		String fromcity_publish = request.getParameter("fromcity_publish");
		String tocity_publish = request.getParameter("tocity_publish");
		if(fromcity_publish !=null && !fromcity_publish.equals("")){
			request.getSession().setAttribute("fromcity_publish", fromcity_publish);
		}
		if(tocity_publish !=null && !tocity_publish.equals("")){
			request.getSession().setAttribute("tocity_publish", tocity_publish);
		}
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
	public ModelAndView searchGoods(HttpServletRequest request,HttpServletResponse response) {	
		String fromcity = request.getParameter("fromcity");
		String tocity = request.getParameter("tocity");
		if(fromcity !=null && !fromcity.equals("")){
			request.getSession().setAttribute("fromcity", fromcity);
		}
		if(tocity !=null && !tocity.equals("")){
			request.getSession().setAttribute("tocity", tocity);
		}
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("search_goods");
		return mv;
	}
	@RequestMapping(value = ("/searchCars"), method = RequestMethod.GET)
	public ModelAndView searchCars(HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		mv.setViewName("search_cars");
		return mv;
	}
	@RequestMapping(value = ("/memberCenter"), method = RequestMethod.GET)
	public ModelAndView memberCenter(HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		mv.setViewName("member_center");
		return mv;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = ("/listFromStation1"), method = RequestMethod.POST)
	public ModelAndView listFromStation1(MsgContentVo msgContentVo,@RequestParam(required = false)String fromcity,@RequestParam(required = false)String tocity,HttpServletResponse response) {	
		msgContentVoList = new ArrayList<Map>();
		msgContentVoNewList = new ArrayList<Map>();
		ModelAndView mv=new ModelAndView();
		msgContentVo.setStation(fromcity +"到"+tocity);
		/*//1=================jackjson
		JsonMsg jsonMsg = null;
		jsonMsg=msgContentService.findMsgContents(msgContentVo.getStation());
		jsonMsg = new JsonMsg(jsonMsg.getStatus(), jsonMsg.getMsg(), jsonMsg.getData());*/
		//2=================list
	    msgContentVoList = msgContentService.findMsgContentsParseList(msgContentVo.getStation());
	    if(msgContentVoList.size() > 0){
	    	for(Map<String, Object> objMap : msgContentVoList){
	    		objMap.put("FromName", getOneCityById(objMap.get("FROM_LOCALE").toString()).getName());
	    		objMap.put("ToName", getOneCityById(objMap.get("TO_LOCALE").toString()).getName());
	    		msgContentVoNewList.add(objMap);
	    	}
	    }
	    mv.addObject("msgContentVoList", msgContentVoNewList);
	    mv.setViewName("goodsList");
		return mv;
	}
	
	@RequestMapping(value = ("/addFromStation"), method = RequestMethod.POST)
	@ResponseBody
	public JsonMsg addFromStation(MsgContentVo msgContentVo,@RequestParam(required = false)String goodId,
			@RequestParam(required = false)String fromLocale,@RequestParam(required = false)String toLocale,@RequestParam(required = false)String goodstype,
			@RequestParam(required = false)String weight,@RequestParam(required = false)String username,
			@RequestParam(required = false)String mobile,@RequestParam(required = false)String remark,HttpServletRequest request,
			HttpServletResponse response) {	
		
		userInfo=(UserInfo)request.getSession().getAttribute("sessionInfo");
		if(goodId!=null && !goodId.equals("")){
			msgContentVo.setId(Long.parseLong(goodId));
		}
		JsonMsg jsonMsg = new JsonMsg();
		//======msgContentVo===============
		msgContentVo.setFrom(fromLocale);
		msgContentVo.setTo(toLocale);
		msgContentVo.setStation(fromLocale+"到"+toLocale);
		msgContentVo.setTitle(fromLocale+"到"+toLocale);
		msgContentVo.setFrom(fromLocale);
		msgContentVo.setTo(toLocale);
		msgContentVo.setType(goodstype);
		msgContentVo.setWeight(Integer.parseInt(weight));
		msgContentVo.setName(username);
		msgContentVo.setMobile(mobile);
		msgContentVo.setPhone(mobile);
		msgContentVo.setUserid(userInfo.getId().toString());
		msgContentVo.setRemark(remark);
		msgContentVo.setPublicationDate(DateTimeHelper.dateTimeToStr(new Date(), DateTimeHelper.DEFAULT_DATE_TIME_FORMATE));
		//======msgContentVo===============
        jsonMsg  = msgContentService.saveMsgContent(msgContentVo, "微信");
		return jsonMsg;
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
	public MsgContent getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(MsgContent msgContent) {
		this.msgContent = msgContent;
	}
	
	
}
