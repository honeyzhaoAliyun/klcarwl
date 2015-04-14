package com.klcarwl.controller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.klcarwl.common.utils.DateUtils;
import com.klcarwl.common.utils.StringUtils;
import com.klcarwl.util.JsonMsg;

public class BaseController {
	
	/**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
	public void printJson(HttpServletResponse response,JsonMsg jsonMsg){
		response.setContentType("text/plain;charset=utf-8");
		JSONObject json=new JSONObject();
		json.put("status",jsonMsg.getStatus());
		json.put("msg",jsonMsg.getMsg());
		json.put("data",jsonMsg.getData());
		PrintWriter out=null;
		try {
			out = response.getWriter();			
			out.print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(out!= null){
				out.flush();
				out.close();				
			}
		}
	}

    /**
     * 用户跳转JSP页面
     * <p/>
     * 此方法不考虑权限控制
     *
     * @param folder 路径
     * @param pageName 页面名称(不加后缀)
     * @return 指定JSP页面
     */
    @RequestMapping("/{folder}/{pageName}")
    public String redirectJsp(@PathVariable String folder, @PathVariable String pageName) {
        return "/" + folder + "/" + pageName;
    }

    /**
     * 用户跳转JSP页面
     * <p/>
     * 此方法不考虑权限控制
     *
     * @param prefix   前缀
     * @param toPage   跳转页面名称（包含目录以及后缀）
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("redirect")
    public void redirectJsp(String prefix, String toPage, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(prefix)) {
            prefix = "/jsp/";
        }
        if (StringUtils.isBlank(toPage)) {
            logger.warn("重定向页面为空!");
            response.sendError(404);
        } else {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("重定向到页面:" + prefix + toPage);
            }
            request.getRequestDispatcher(prefix + toPage).forward(request, response);
        }
    }

    /**
     * 添加Model消息
     *
     * @param messages 消息
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute("message", sb.toString());
    }

    /**
     * 添加Flash消息
     *
     * @param messages 消息
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        redirectAttributes.addFlashAttribute("message", sb.toString());
    }

    /**
     * 初始化数据绑定
     * 1. 设置被排除的属性 不自动绑定
     * 2. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 3. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

	public BaseController() {
		super();
		// TODO Auto-generated constructor stub
	}
    

}