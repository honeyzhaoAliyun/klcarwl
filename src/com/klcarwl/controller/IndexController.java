package com.klcarwl.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.klcarwl.common.utils.expressions.FinalStr;
import com.klcarwl.model.Activity;
import com.klcarwl.model.ActivityLog;
import com.klcarwl.model.Result;
import com.klcarwl.model.SysLocale;
import com.klcarwl.model.UserActivity;
import com.klcarwl.model.UserInfo;
import com.klcarwl.model.ValidateLog;
import com.klcarwl.service.ActivityLogService;
import com.klcarwl.service.ActivityService;
import com.klcarwl.service.SysLocaleService;
import com.klcarwl.service.UserActivityService;
import com.klcarwl.service.UserInfoService;
import com.klcarwl.service.ValidateLogService;
import com.klcarwl.util.ConstantUtil;
import com.klcarwl.util.RestUtil;
import com.klcarwl.vo.WechatUser;

@Controller
public class IndexController extends BaseController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private SysLocaleService sysLocaleService;
	@Autowired
	private ValidateLogService validateLogService;
	@Autowired
	private ActivityLogService activityLogService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private UserActivityService  userActivityService;
	
	private UserInfo userInfo;
	
	private List<SysLocale> sysLocaleList;
	
	private Activity activity;
	
	private ActivityLog activityLog;
	
	private UserActivity userActivity;
	
	private List<Activity> activityList;
	
	private List<ActivityLog> activityLogList;
	
	private List<UserActivity> userActivityList;
	
	private Double sumCost = 0D; //B帮助A抢到的金额
	
	private Double BsumCost =0D; //B当前累计抢到的话费总额
	
	@RequestMapping(value = ("/index"), method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		mv.setViewName("index");
		return mv;
	}
	@RequestMapping(value = ("/main"), method = RequestMethod.GET)
	public ModelAndView main(HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		mv.setViewName("main");
		return mv;
	}
	@RequestMapping(value = ("/top"), method = RequestMethod.GET)
	public ModelAndView top(HttpServletResponse response) {	
		ModelAndView mv=new ModelAndView();
		mv.setViewName("top");
		return mv;
	}
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
    @RequestMapping(value = {"login"}, method = RequestMethod.POST)
    public Result login(@RequestParam(required = true) String loginName, @RequestParam(required = true) String password,
                        HttpServletRequest request) {
		userInfo = new UserInfo();
		Result result = null;
        String msg = null;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserInfo.class);
		detachedCriteria.add(Restrictions.eq("userName",loginName));
		detachedCriteria.add(Restrictions.eq("password",DigestUtils.md5Hex(password)));
		List userInfos=userInfoService.find(detachedCriteria);
		if(userInfos.size() >0){
			userInfo = (UserInfo) userInfos.get(0);
		}
		if (userInfo.getId() == null) {
            msg = "用户名或密码不正确!";
        }
        if (msg != null) {
            result = new Result(Result.ERROR, msg, null);
        } else {
            //将用户信息放入session中
            request.getSession().setAttribute("sessionInfo", userInfos.get(0));
            logger.info("用户{}登录系统,IP:{}.", userInfo.getUserName(), null);

            //设置调整URL 如果session中包含未被授权的URL 则跳转到该页面
            String resultUrl = ConstantUtil.get("WULIU_IP") + "/klcarwl/index.do";
            //返回
            result = new Result(Result.SUCCESS, "用户验证通过!", resultUrl);
            logger.info("用户验证通过",result.toString());
        }
        return result;
    }
	@RequestMapping(value = ("/loginpage"), method = RequestMethod.GET)
	public ModelAndView loginpage(HttpServletRequest request,HttpServletResponse response) {	
		ModelAndView mav=new ModelAndView();
		Object openid = request.getSession().getAttribute("openid");
		WechatUser wechatUser  = getOneWechatObject(openid.toString());
		mav.addObject("wechatUser", wechatUser);
		mav.setViewName("login");
		return mav;
	}
	@RequestMapping(value = ("/register"), method = RequestMethod.GET)
	public ModelAndView regedit(HttpServletRequest request,HttpServletResponse response) {	
		ModelAndView mav=new ModelAndView();
		mav.setViewName("register");
		return mav;
	}
	/**
	 * 找回密码页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = ("/_forgetpasswordinput"), method = RequestMethod.GET)
	public ModelAndView _forgetpasswordinput(HttpServletRequest request,HttpServletResponse response) {	
		ModelAndView mav=new ModelAndView();
		mav.setViewName("forget_password");
		return mav;
	}
	/**
	 * 找回密码提交
	 */
	@ResponseBody
	@RequestMapping(value = ("/_forgetpassword"), method = RequestMethod.POST)
    public Result forgetpassword(@RequestParam(value="mobilephone") String mobilephone,@RequestParam(value="validateCode") String validateCode,@ModelAttribute("model") UserInfo user,HttpServletRequest request) throws Exception {
    	Result result = null;
        user.setMobile(mobilephone);
        user.setIsUse("1");
        UserInfo mobileCheckuser = userInfoService.getUserInfoByUsername(mobilephone);
        //验证码验证
        if(validateCode.equals("")){
        	result = new Result(Result.WARN, "验证码不能为空!", "validateCode");
            logger.debug(result.toString());
            return result;
        }
        //验证码是否正确
        ValidateLog validateLog = validateLogService.getValidateLogBySendTo(user.getMobile());
        if(! validateCode.equals(validateLog.getValidateCode())){
        	result = new Result(Result.WARN, "验证码不正确!", "validateCode");
            logger.debug(result.toString());
            return result;
        }
        
        // 名称重复校验
        if (user.getMobile() != null && mobileCheckuser ==null ) {
            result = new Result(Result.WARN, "手机号为[" + user.getMobile() + "]用户不存在,请修正!", "mobilephone");
            logger.debug(result.toString());
            return result;
        }
        
        //设置调整URL 如果session中包含未被授权的URL 则跳转到该页面
        String resultUrl = ConstantUtil.get("WULIU_IP") + "/klcarwl/_resetpasswordinput.do?ids="+mobileCheckuser.getId()+"";
        //返回
        result = new Result(Result.SUCCESS, "用户验证通过!", resultUrl);
        logger.info("用户验证通过",result.toString());
        return result;
    }
	
	/**
     * 重置密码详细页
     */
    @RequestMapping(value = ("/_resetpasswordinput"), method = RequestMethod.GET)
    public ModelAndView resetpasswordinput(@RequestParam(value = "ids", required = false)List<Long> ids,@ModelAttribute("model") UserInfo user,HttpServletRequest request) {
    	ModelAndView mav  = new ModelAndView("reset_password");
    	user=userInfoService.get(ids.get(0));
		mav.addObject("user",user);
    	return mav;
    }
    /**
     * 重置密码
     */
    @RequestMapping(value = ("/_resetpassword"), method = RequestMethod.POST)
    @ResponseBody
    public Result resetpassword(@RequestParam(value = "ids", required = false)List<Long> ids,@RequestParam(value="password") String password,@RequestParam(value="confirmpassword") String confirmpassword,@ModelAttribute("model") UserInfo user,HttpServletRequest request) {
    	Result result = null;
    	user=userInfoService.get(ids.get(0));
    	//密码不对
        if(! password.equals(confirmpassword)){
        	result = new Result(Result.WARN, "确认密码与密码不同!", "confirmpassword");
            logger.debug(result.toString());
            return result;
        }
        //--------密码修改 begin----------------------
        user.setPassword(DigestUtils.md5Hex(password));
        
        userInfoService.update(user);
        
        //--------密码修改 end----------------------
        //设置调整URL 如果session中包含未被授权的URL 则跳转到该页面
      //设置调整URL 如果session中包含未被授权的URL 则跳转到该页面
        String resultUrl = ConstantUtil.get("WULIU_IP") + "/klcarwl/loginpage.do";
        //返回
        result = new Result(Result.SUCCESS, "密码重置成功!", resultUrl);
        logger.info("密码重置成功",result.toString());
        //返回
    	return result;
    }
	
	/**
     * 个人用户注册 
     */
	@RequestMapping(value = ("/_save"), method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestParam(value="mobilephone") String mobilephone,@RequestParam(value="password") String password,@RequestParam(value="confirmpassword") String confirmpassword,@RequestParam(value="validateCode") String validateCode,@ModelAttribute("model") UserInfo user,HttpServletRequest request) {
        Result result = null;
        user.setPassword(password);
        user.setMobile(mobilephone);
        user.setIsUse("1");
        UserInfo mobileCheckuser = userInfoService.getUserInfoByUsername(mobilephone);
        //密码不对
        if(! user.getPassword().equals(confirmpassword)){
        	result = new Result(Result.WARN, "确认密码与密码不同!", "confirmpassword");
            logger.debug(result.toString());
            return result;
        }
        //验证码验证
        if(validateCode.equals("")){
        	result = new Result(Result.WARN, "验证码不能为空!", "validateCode");
            logger.debug(result.toString());
            return result;
        }
        //方式1---验证码验证
        /*HttpSession session = request.getSession();
        String captchaId = session.getId();
        String captchaCode = (String)session.getAttribute(captchaId);
        if(!validateCode.equalsIgnoreCase(captchaCode)){
        	result = new Result(Result.WARN, "验证码不正确!", "validateCode_qy");
            logger.debug(result.toString());
            return result;
        }*/
        //方式2---验证码是否正确
        ValidateLog validateLog = validateLogService.getValidateLogBySendTo(user.getMobile());
        if(! validateCode.equals(validateLog.getValidateCode())){
        	result = new Result(Result.WARN, "验证码不正确!", "validateCode");
            logger.debug(result.toString());
            return result;
        }
        
        // 名称重复校验
        if (user.getMobile() != null && mobileCheckuser !=null ) {
            result = new Result(Result.WARN, "手机号为[" + user.getMobile() + "]已存在,请修正!", "mobile");
            logger.debug(result.toString());
            return result;
        }
        
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        
        //登录名可以是手机号也可以邮箱
        user.setUserName(user.getMobile());
        user.setName(user.getMobile());
        user.setStatus("1");
        
        userInfoService.saveUser(user);
        //设置调整URL 如果session中包含未被授权的URL 则跳转到该页面
        String resultUrl = ConstantUtil.get("WULIU_IP") + "/klcarwl/index.do";
        //返回
        result = new Result(Result.SUCCESS, "用户注册通过!", resultUrl);
        logger.info("用户注册通过",result.toString());
        return result;
    }
	
	@RequestMapping(value = ("/restWord"), method = RequestMethod.GET)
	public ModelAndView restWord(HttpServletRequest request,HttpServletResponse response) {	
		ModelAndView mav=new ModelAndView();
		mav.setViewName("rest_word");
		return mav;
	}
	@RequestMapping(value = ("/down"), method = RequestMethod.GET)
	public ModelAndView down(HttpServletRequest request,HttpServletResponse response) {	
		ModelAndView mav=new ModelAndView();
		mav.setViewName("down");
		return mav;
	}
	@RequestMapping(value = ("/aboutUs"), method = RequestMethod.GET)
	public ModelAndView aboutUs(HttpServletRequest request,HttpServletResponse response) {	
		ModelAndView mav=new ModelAndView();
		mav.setViewName("aboutUs");
		return mav;
	}
	@RequestMapping(value = ("/contractUs"), method = RequestMethod.GET)
	public ModelAndView contractUs(HttpServletRequest request,HttpServletResponse response) {	
		ModelAndView mav=new ModelAndView();
		mav.setViewName("contractUs");
		return mav;
	}
	
	@RequestMapping(value = ("/loginOut"), method = RequestMethod.GET)
	public ModelAndView loginOut(HttpServletRequest request,HttpServletResponse response) {		
		ModelAndView mav=new ModelAndView();
		request.getSession().removeAttribute("sessionInfo");
		mav.setViewName("login");
		return mav;
	}
	
	@RequestMapping(value = ("/weizhang"), method = RequestMethod.GET)
	public ModelAndView weizhang(HttpServletRequest request,HttpServletResponse response) {		
		ModelAndView mav=new ModelAndView();
		mav.setViewName("search_weizhang");
		return mav;
	}
	@RequestMapping(value = ("/sysLocaleList"), method = RequestMethod.GET)
	public ModelAndView cityList(HttpServletRequest request,HttpServletResponse response,@RequestParam(required = false) String type,@RequestParam(required = false) String id,@RequestParam(required = false) String resource,@RequestParam(required = false) String publishType) {		
		ModelAndView mav=new ModelAndView();
		sysLocaleList = new ArrayList<SysLocale>();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
		if(type !=null && !type.equals("")){
			detachedCriteria.add(Restrictions.eq("type",type));
		}
		if(id !=null && !id.equals("") && !type.equals(1)){
			detachedCriteria.add(Restrictions.eq("parent",Long.parseLong(id)));
		}
		if(resource ==null || resource.equals("")){
			resource ="1";
		}
		if(publishType ==null || publishType.equals("")){
			publishType ="1";
		}
		request.setAttribute("resource", resource);
		request.setAttribute("publishType", publishType);
		
		sysLocaleService.find(detachedCriteria);
		sysLocaleList =sysLocaleService.find(detachedCriteria);
		
		mav.addObject("sysLocaleList",sysLocaleList);
		mav.setViewName("sysLocaleList");
		return mav;
	}
	
	
	
	/**
	 *根据微信openID 
	 * @param key
	 * @return
	 */
	public WechatUser getOneWechatObject(String openid){
		WechatUser wechatUser = new WechatUser();
		String results = "";
		if(openid !=null && openid !=""){
			/**
			 * @author honey.zhao@aliyun.com
			 * describe:获取微信公众号，通过【当前微信用户openID】获取微信用户信息
			 *  {@link RestUtil.getUserInfo}
			 */
			results = RestUtil.getInvoke("openid",openid);
			JSONObject result = new JSONObject();
			if (results != "" || results.equals("0") ) {
				result = JSONObject.fromObject(results);
				JSONObject openIdUser = JSONObject.fromObject(result.get("openIdUser"));
				wechatUser.setSubscribe(openIdUser.getString("subscribe"));
				wechatUser.setOpenid(openIdUser.getString("openid"));
				wechatUser.setSex(openIdUser.getInt("sex"));
				wechatUser.setNickname(openIdUser.getString("nickname"));
				wechatUser.setLanguage(openIdUser.getString("language"));
				wechatUser.setCity(openIdUser.getString("city"));
				wechatUser.setProvince(openIdUser.getString("province"));
				wechatUser.setCountry(openIdUser.getString("country"));
				wechatUser.setHeadimgurl(openIdUser.getString("headimgurl"));
				wechatUser.setSubscribe_time(openIdUser.getString("subscribe"));
			}
		}
		return wechatUser;
	}
	
	 	/**
	 	 * --找回密码
		 * 验证用户修改邮箱或者密码
		 * @return
		 */
	    @RequestMapping(value = {"_sendValidateWithAjax"})
	    @ResponseBody
		public Result sendValidateWithAjax(@RequestParam(value="sendTo") String sendTo,@RequestParam(value="type") String type,@RequestParam(value="code") String code,HttpServletRequest request,@RequestParam(value = "ids")String ids){
	    	Result result = null;
	    	if("1".equals(type)){
				String pattern="^((?<=^)|(?<=\\s))[-\\w]+([-.]\\w+)*@\\w+([-.]\\w+)*\\.([A-Za-z])+$";
				if(!checkNumber(pattern,sendTo)){
					result = new Result(Result.ERROR, "邮箱格式有误！", null);
					return result;
				}
			}else if("2".equals(type)){
				if(!checkNumber(FinalStr.MOBILE_PATTERN,sendTo)){
					result = new Result(Result.ERROR, "手机号格式有误！", null);
					return result;
				}
			}else{
				result = new Result(Result.ERROR, "请选择接受验证码通道类型", null);
				return result;
			}
	    	
	    	ValidateLog validateLog=validateLogService.getValidateLogBySendTo(sendTo);
	    	
	    	long mintime = new Date().getTime() - validateLog.getModifyDate().getTime();
	    	long day=mintime/(24*60*60*1000);
	    	long hour=(mintime/(60*60*1000)-day*24);
	    	long min=((mintime/(60*1000))-day*24*60-hour*60);
	    	long s=(mintime/1000-day*24*60*60-hour*60*60-min*60);
	    	//System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
	    	/**
	    	 * 5分钟才可下发短信
	    	 */
	    	if(day<=0 && hour<=0 && min<5){
	    		result = new Result(Result.ERROR, "5分钟后再次下发请求验证码！", null);
    			return result;
	    	}else{
	    		validateLogService.saveAndSendValidateMsg(type, sendTo, ids);
	    		return Result.successResult();
	    	}
		}
	    /**
	     * --注册
	     * 验证用户修改邮箱或者密码
	     * @return
	     */
	    @RequestMapping(value = {"_sendRegisterValidateWithAjax"})
	    @ResponseBody
	    public Result sendRegisterValidateWithAjax(@RequestParam(value="sendTo") String sendTo,@RequestParam(value="type") String type,@RequestParam(value="code") String code,HttpServletRequest request,@RequestParam(value = "ids")String ids){
	    	Result result = null;
	    	if("1".equals(type)){
	    		String pattern="^((?<=^)|(?<=\\s))[-\\w]+([-.]\\w+)*@\\w+([-.]\\w+)*\\.([A-Za-z])+$";
	    		if(!checkNumber(pattern,sendTo)){
	    			result = new Result(Result.ERROR, "邮箱格式有误！", null);
	    			return result;
	    		}
	    	}else if("2".equals(type)){
	    		if(!checkNumber(FinalStr.MOBILE_PATTERN,sendTo)){
	    			result = new Result(Result.ERROR, "手机号格式有误！", null);
	    			return result;
	    		}
	    	}else{
	    		result = new Result(Result.ERROR, "请选择接受验证码通道类型", null);
	    		return result;
	    	}
	    	/**
	    	 * honey.zhao@aliyun.com
	    	 * 判断手机号是否注册过--不要浪费短信数量 ---哇咔咔
	    	 */
	    	UserInfo mobileCheckuser = userInfoService.getUserInfoByUsername(sendTo);
	    	// 名称重复校验
	        if (mobileCheckuser !=null ) {
	            result = new Result(Result.WARN, "手机号为[" + sendTo + "]已存在,请修正!", "mobile");
	            logger.debug(result.toString());
	            return result;
	        }else{
	        	validateLogService.saveAndSendValidateMsg(type, sendTo, ids);
	        	return Result.successResult();
	        }
	    }
		/**
		 * 用于验证
		 * @return
		 */
	    @RequestMapping(value = {"_validateWithAjax"})
	    @ResponseBody
		public Result validateWithAjax(@RequestParam(value="sendTo") String sendTo,@RequestParam(value="type") String type,@RequestParam(value="code") String code,HttpServletRequest request){
	    	Result result = null;
	    	request.setAttribute("sendTo",sendTo);
	    	request.setAttribute("type",type);
			if("1".equals(type)){
				String pattern="^((?<=^)|(?<=\\s))[-\\w]+([-.]\\w+)*@\\w+([-.]\\w+)*\\.([A-Za-z])+$";
				if(!checkNumber(pattern,sendTo)){
					result = new Result(Result.ERROR, "邮箱格式有误！", null);
					return result;
				}
			}else if("2".equals(type)){
				if(!checkNumber(FinalStr.MOBILE_PATTERN,sendTo)){
					result = new Result(Result.ERROR, "邮箱格式有误！", null);
					return result;
				}
			}else{
				result = new Result(Result.ERROR, "请选择接受验证码通道类型！", null);
				return result;
			}
			return Result.successResult();
		}
	    /**
	     * ==========抢话费活动首页index=================
	     * @param openidA 抢话费发起者
	     * @param openidB 帮助A抢话费 的人员B
	     * @param request
	     * @param response
	     * @return
	     */
	    @RequestMapping(value = ("/activity"), method = RequestMethod.GET)
		public ModelAndView activity(@RequestParam(value="openidA",required = false) String openidA,@RequestParam(value="openidB",required = false) String openidB,HttpServletRequest request,HttpServletResponse response) {	
	    	ModelAndView mav=new ModelAndView();
			openidB =request.getSession().getAttribute("openid").toString();
			if(openidA ==null){
			}else{
				/*第1步-----获取活动发起者 A用户信息-------*/
				UserInfo userInfoA = new UserInfo();
		        DetachedCriteria dc = DetachedCriteria.forClass(UserInfo.class);
		        dc.add(Restrictions.eq("wechatKey", openidA));
				List<UserInfo> userInfoBList = userInfoService.find(dc);
		        if(userInfoBList.size() >0){
		        	userInfoA = userInfoBList.get(0);
		        	//2)为自己抢话费记录===获取当前用户A 抢话费记录
		        	DetachedCriteria dcA = DetachedCriteria.forClass(UserActivity.class);
		        	dcA.add(Restrictions.eq("parentUserInfo",userInfoA ));
		        	userActivityList = userActivityService.find(dcA);
		        	mav.addObject("userActivityList", userActivityList);
		        }
			}
			if(openidB ==null){
			}else{
				/*第2步--获取 当前参与帮助A抢话费的  B用户信息----*/
				/*第3步--插入当前用户活动访问日志-------------*/
				ActivityLog activityLog = new ActivityLog();
				activityLog.setOpenid(openidB);
				activityLog.setCreateDate(new Date());
				activityLog.setModifyDate(new Date());
				activityLog.setIsUse("1");
				activityLog.setContent("");
				
				activityLogService.save(activityLog);
				logger.info("==登录日志：openidB["+openidB+"]访问活动");
			}
			
			/*第4步--session存储A 、B用户信息---------------*/
			request.getSession().setAttribute("openidA", openidA);
			request.getSession().setAttribute("openidB", openidB);
			//========判断B是否参加过活动============
	        DetachedCriteria dc = DetachedCriteria.forClass(UserInfo.class);
	        dc.add(Restrictions.eq("wechatKey", request.getSession().getAttribute("openidB")));
			List<UserInfo> userInfoBList = userInfoService.find(dc);
	        if(userInfoBList.size() >0){
	        	mav.setView(new RedirectView("_woindex.do"));
	        }else{
	        	mav.setViewName("activity_index");
	        }
			return mav;
		}
	    /**
	     * ==========抢话费结果页=================
	     * @param openidA 抢话费发起者
	     * @param openidB 帮助A抢话费 的人员B
	     * @param request
	     * @param response
	     * @return
	     */
	    @RequestMapping(value = ("/robbing"), method = RequestMethod.GET)
		public ModelAndView robbing(@RequestParam(value="openidA",required = false) String openidA,@RequestParam(value="openidB",required = false) String openidB,@RequestParam(value="sumtip",required = false) String sumtip,HttpServletRequest request,HttpServletResponse response) {	
	    	logger.info("==========抢话费结果页=================");
	    	ModelAndView mav=new ModelAndView();
			if(sumtip !=null && !sumtip.equals("")){
				if(Integer.parseInt(sumtip) >0 ){
					sumCost = Integer.parseInt(sumtip) * 0.015;
				}
				DecimalFormat df = new DecimalFormat("######0.00");
				logger.info("B帮助A抢到金额为："+df.format(sumCost)+"元");
				mav.addObject("sumCost", df.format(sumCost));
			}
			
			mav.setViewName("activity_robbing");
			return mav;
		}
	    /**
	     * ==========获奖人员列表=================
	     * @param openidA 抢话费发起者
	     * @param openidB 帮助A抢话费 的人员B
	     * @param request
	     * @param response
	     * @return
	     */
	    @RequestMapping(value = ("/winners"), method = RequestMethod.GET)
		public ModelAndView winners(@RequestParam(value="openidA",required = false) String openidA,@RequestParam(value="openidB",required = false) String openidB,HttpServletRequest request,HttpServletResponse response) {	
	    	logger.info("==========获奖人员列表=================");
	    	ModelAndView mav=new ModelAndView();
	    	
	    	DetachedCriteria dc = DetachedCriteria.forClass(UserInfo.class);
	        dc.add(Restrictions.eq("status", "1"));
	        dc.add(Restrictions.ne("userName", "admin"));
	    	List<UserInfo> userInfoList = userInfoService.find(dc);
	    	mav.addObject("userInfoList", userInfoList);
			mav.setViewName("activity_winners");
			return mav;
		}
	    /**
	     * ==========活动注册邦定=================
	     * @param openidA 抢话费发起者
	     * @param openidB 帮助A抢话费 的人员B
	     * @param request
	     * @param response
	     * @return
	     */
	    @RequestMapping(value = ("/registerActivity"), method = RequestMethod.GET)
	    public ModelAndView registerActivity(@RequestParam(value="sumCost",required = false) String sumCost,@RequestParam(value="openidA",required = false) String openidA,@RequestParam(value="openidB",required = false) String openidB,HttpServletRequest request,HttpServletResponse response) {	
	    	logger.info("==========活动注册邦定=================");
	    	ModelAndView mav=new ModelAndView();
	    	mav.addObject("sumCost", sumCost);
	    	mav.setViewName("activity_register");
	    	return mav;
	    }
	    

		/**
	     * 活动---个人用户注册 
	     */
		@RequestMapping(value = ("/_saveActivity"), method = RequestMethod.POST)
	    @ResponseBody
	    public Result saveActivity(@RequestParam(value="sumCost",required=false) String sumCost,@RequestParam(value="mobilephone") String mobilephone,@RequestParam(value="validateCode") String validateCode,@ModelAttribute("model") UserInfo user,HttpServletRequest request) {
	        Result result = null;
	        user.setPassword("123456");
	        user.setMobile(mobilephone);
	        user.setIsUse("1");
	        user.setName(mobilephone);
	        if(request.getSession().getAttribute("openidB") !=null){
	        	user.setNickName(getOneWechatObject(request.getSession().getAttribute("openidB").toString()).getNickname());//----应要求 name 要保存微信昵称
	        }
	        UserInfo mobileCheckuser = userInfoService.getUserInfoByUsername(mobilephone);
	        
	       /* //验证码验证
	        if(validateCode.equals("")){
	        	result = new Result(Result.WARN, "验证码不能为空!", "validateCode");
	            logger.debug(result.toString());
	            return result;
	        }
	        
	        //方式2---验证码是否正确
	        ValidateLog validateLog = validateLogService.getValidateLogBySendTo(user.getMobile());
	        if(! validateCode.equals(validateLog.getValidateCode())){
	        	result = new Result(Result.WARN, "验证码不正确!", "validateCode");
	            logger.debug(result.toString());
	            return result;
	        }*/
	        
	        // 名称重复校验
	        if (user.getMobile() != null && mobileCheckuser !=null ) {
	            result = new Result(Result.WARN, "手机号为[" + user.getMobile() + "]已存在,请修正!", "mobile");
	            logger.debug(result.toString());
	            return result;
	        }
	        
	        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
	        
	        //登录名可以是手机号也可以邮箱
	        user.setUserName(user.getMobile());
	        user.setStatus("1");
	        user.setWechatKey(request.getSession().getAttribute("openidB").toString());
	        
	        userInfoService.saveUser(user);
	        //设置调整URL 如果session中包含未被授权的URL 则跳转到该页面
	        //活动验证成功页面
	        StringBuffer resultUrl = new StringBuffer();
	        resultUrl.append(ConstantUtil.get("WULIU_IP") + "/klcarwl/_validateSuccess.do");
	        if(sumCost !=null){
	        	resultUrl.append("?sumCost="+sumCost);
	        }
	        //返回
	        result = new Result(Result.SUCCESS, "用户注册通过!", resultUrl.toString());
	        logger.info("用户注册通过",result.toString());
	        /**
	         * 1)获取最新活动
	         */
	        activityList = activityService.getAll();
	        if(activityList.size() >0){
	        	activity = activityList.get(activityList.size() -1);
	        }else{
				activity = new Activity();
				activity.setTitle("首波10万话费来袭");
				activity.setBeginDate(new Date());
				
				activity.setEndDate(new Date());
				activity.setCreateDate(new Date());
				activity.setModifyDate(new Date());
				activity.setIsUse("1");
				activity.setContent("");
				activityService.save(activity);
				logger.info("活动添加成功");
				
	        }
	        /**
	         * 2)获取openidA 相应的用户
	         */
	        UserInfo userInfoA = new UserInfo();
	        DetachedCriteria dc = DetachedCriteria.forClass(UserInfo.class);
	        dc.add(Restrictions.eq("wechatKey", request.getSession().getAttribute("openidA")));
	        List<UserInfo> userInfoAList = userInfoService.find(dc);
	        if(userInfoAList.size() >0){
	        	userInfoA = userInfoAList.get(0);
	        }
	        /**
	         * 3)填写活动抢话费与用户中间表 ---相对于A的操作
	         */
	        UserActivity userActivity = new UserActivity();
	        userActivity.setActivity(activity);
	        userActivity.setUserInfo(user);
	        userActivity.setParentUserInfo(userInfoA);
	        userActivity.setCurrentCost(userInfoA.getCurrentCost());
	        userActivity.setHelpCost(new Double(sumCost));
	        
	        userActivity.setCreateDate(new Date());
	        userActivity.setModifyDate(new Date());
	        userActivity.setIsUse("1");
	        userActivity.setContent("");
	        userActivity.setStatus(0);
	        logger.info("填写活动抢话费与用户中间表 ---相对于A的操作-成功");
	        /**
	         * 4)相对于b的操作  自己也抢到话费 XX元
	         */
	        UserActivity userActivityB = new UserActivity();
	        userActivityB.setActivity(activity);
	        userActivityB.setUserInfo(user);
	        userActivityB.setParentUserInfo(user);
	        userActivityB.setCurrentCost(user.getCurrentCost());
	        userActivityB.setHelpCost(new Double(sumCost));
	        
	        userActivityB.setCreateDate(new Date());
	        userActivityB.setModifyDate(new Date());
	        userActivityB.setIsUse("1");
	        userActivityB.setContent("");
	        userActivityB.setStatus(0);
	        
	        userActivityService.save(userActivity);
	        userActivityService.save(userActivityB);
	        logger.info("相对于b的操作  自己也抢到话费 XX元-成功");
	        
	        return result;
	    }
		
		@RequestMapping(value = ("/_validateSuccess"), method = RequestMethod.GET)
		public ModelAndView _validateSuccess(@RequestParam(value="sumCost",required=false) String sumCost,HttpServletRequest request,HttpServletResponse response) {	
			logger.info("============验证成功页面============");
			userActivityList = new ArrayList<UserActivity>();
			ModelAndView mav=new ModelAndView();
			if(sumCost !=null){
				mav.addObject("sumCost", sumCost);
			}
			Object object = request.getSession().getAttribute("openidB");
			if(object !=null){
				//1)为自己抢话费记录===获取当前用户B的信息
				UserInfo userInfoB = new UserInfo();
		        DetachedCriteria dc = DetachedCriteria.forClass(UserInfo.class);
		        dc.add(Restrictions.eq("wechatKey", request.getSession().getAttribute("openidB")));
				List<UserInfo> userInfoBList = userInfoService.find(dc);
		        if(userInfoBList.size() >0){
		        	userInfoB = userInfoBList.get(0);
		        	//2)为自己抢话费记录===获取当前用户B 抢话费记录
		        	DetachedCriteria dcB = DetachedCriteria.forClass(UserActivity.class);
		        	dcB.add(Restrictions.eq("parentUserInfo",userInfoB ));
		        	userActivityList = userActivityService.find(dcB);
		        	
		        	mav.addObject("userActivityList", userActivityList);
		        }
		        //B 作为 分享者此时 则变成了 A
		        mav.addObject("openidA", object.toString());
		        mav.addObject("jsapi_ticket", request.getSession().getAttribute("jsapi_ticket"));
			}
			mav.setViewName("activity_validateSuccess");
			return mav;
		}
		
		
		/**
		 * 登陆的当前b 用户 参加过活动自动调转页面 [请好友为我抢话费] 页面
		 */
		@RequestMapping(value = ("/_woindex"), method = RequestMethod.GET)
		public ModelAndView _woindex(HttpServletRequest request,HttpServletResponse response) {
			ModelAndView mav=new ModelAndView();
			Object object = request.getSession().getAttribute("openidB");
			if(object !=null){
				//1)为自己抢话费记录===获取当前用户B的信息
				UserInfo userInfoB = new UserInfo();
		        DetachedCriteria dc = DetachedCriteria.forClass(UserInfo.class);
		        dc.add(Restrictions.eq("wechatKey", request.getSession().getAttribute("openidB")));
				List<UserInfo> userInfoBList = userInfoService.find(dc);
		        if(userInfoBList.size() >0){
		        	userInfoB = userInfoBList.get(0);
		        	//2)为自己抢话费记录===获取当前用户B 抢话费记录
		        	DetachedCriteria dcB = DetachedCriteria.forClass(UserActivity.class);
		        	dcB.add(Restrictions.eq("parentUserInfo",userInfoB ));
		        	userActivityList = userActivityService.find(dcB);
		        	//计算B抢话费总额
		        	BsumCost = 0D;
		        	if(userActivityList.size() >0){
		        		for(UserActivity userActivity : userActivityList){
		        			BsumCost =BsumCost +userActivity.getHelpCost();
		        		}
		        		mav.addObject("BsumCost", BsumCost);
		        	}
		        	mav.addObject("userActivityList", userActivityList);
		        	mav.addObject("jsapi_ticket", request.getSession().getAttribute("jsapi_ticket"));
		        }
		        //B 作为 分享者此时 则变成了 A
		        mav.addObject("openidA", object.toString());
		        
			}
			mav.setViewName("activity_woindex");
			
			return mav;	
		}
		
		/**
		 * 登陆的当前b 用户 参加过活动自动调转页面 [请好友为我抢话费] 页面
		 */
		@RequestMapping(value = ("/_wo"), method = RequestMethod.GET)
		public ModelAndView _wo(HttpServletRequest request,HttpServletResponse response) {
			ModelAndView mav=new ModelAndView();
			Object object = request.getSession().getAttribute("openidB");
			if(object !=null){
				//1)为自己抢话费记录===获取当前用户B的信息
				UserInfo userInfoB = new UserInfo();
		        DetachedCriteria dc = DetachedCriteria.forClass(UserInfo.class);
		        dc.add(Restrictions.eq("wechatKey", request.getSession().getAttribute("openidB")));
				List<UserInfo> userInfoBList = userInfoService.find(dc);
		        if(userInfoBList.size() >0){
		        	userInfoB = userInfoBList.get(0);
		        	//2)为自己抢话费记录===获取当前用户B 抢话费记录
		        	DetachedCriteria dcB = DetachedCriteria.forClass(UserActivity.class);
		        	dcB.add(Restrictions.eq("parentUserInfo",userInfoB ));
		        	userActivityList = userActivityService.find(dcB);
		        	//计算B抢话费总额
		        	BsumCost = 0D;
		        	if(userActivityList.size() >0){
		        		for(UserActivity userActivity : userActivityList){
		        			BsumCost =BsumCost +userActivity.getHelpCost();
		        		}
		        		mav.addObject("BsumCost", BsumCost);
		        	}
		        	mav.addObject("userActivityList", userActivityList);
		        	mav.addObject("jsapi_ticket", request.getSession().getAttribute("jsapi_ticket"));
		        }
		        //B 作为 分享者此时 则变成了 A
		        mav.addObject("openidA", object.toString());
		        
			}
			mav.setViewName("activity_wo");
			
			return mav;	
		}
	    
		
		/**
		 * 验证
		 * @param pattern
		 * @param str
		 * @return
		 */
		public static boolean checkNumber(String pattern,String str){
			str=str==null?"":str;
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
			return m.find();
		}
	
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public List<SysLocale> getSysLocaleList() {
		return sysLocaleList;
	}
	public void setSysLocaleList(List<SysLocale> sysLocaleList) {
		this.sysLocaleList = sysLocaleList;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public ActivityLog getActivityLog() {
		return activityLog;
	}
	public void setActivityLog(ActivityLog activityLog) {
		this.activityLog = activityLog;
	}
	public List<Activity> getActivityList() {
		return activityList;
	}
	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}
	public List<ActivityLog> getActivityLogList() {
		return activityLogList;
	}
	public void setActivityLogList(List<ActivityLog> activityLogList) {
		this.activityLogList = activityLogList;
	}
	public Double getSumCost() {
		return sumCost;
	}
	public void setSumCost(Double sumCost) {
		this.sumCost = sumCost;
	}
	public UserActivity getUserActivity() {
		return userActivity;
	}
	public void setUserActivity(UserActivity userActivity) {
		this.userActivity = userActivity;
	}
	public List<UserActivity> getUserActivityList() {
		return userActivityList;
	}
	public void setUserActivityList(List<UserActivity> userActivityList) {
		this.userActivityList = userActivityList;
	}
	public Double getBsumCost() {
		return BsumCost;
	}
	public void setBsumCost(Double bsumCost) {
		BsumCost = bsumCost;
	}
	
}
