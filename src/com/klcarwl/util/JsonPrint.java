package com.klcarwl.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class JsonPrint {
	public static void print(HttpServletResponse response,JSONObject object,String callback){
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.print(callback + "(" + object + ")");
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
	public static void printJson(HttpServletResponse response,JsonMsg jsonMsg){
		response.setContentType("text/html;charset=utf-8");
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
}
