package com.klcarwl.util;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class JsoupUtil {
	public static Element getBody(String host,String url) throws IOException{
		String theDate=DateUtil.DateFormat(new Date(), DateUtil.allDatePartern);
		Document doc2 = Jsoup.connect(url)
				.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
				.header("Accept-Encoding","gzip, deflate")
				.header("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3")
				.header("Cache-Control","max-age=0")
				.header("Connection","keep-alive")
				.header("Cookie","lastnewinfonum1="+theDate+"|472; lastnewinfonum2="+theDate+"|5; lastnewinfonum3="+theDate+"|0; lastnewinfonum4="+theDate+"|1; lastnewinfonum5="+theDate+"|0; lastnewinfonum6="+theDate+"|4; lastnewinfonum7="+theDate+"|0; lastnewinfonum8="+theDate+"|0")
				.header("Host",host)
				.header("User-Agent","Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
				.get();
		Element body=doc2.body();
		return body;
	}
	
	public static Element getBody(String host,String url,Map<String,String> head) throws IOException{
		String theDate=DateUtil.DateFormat(new Date(), DateUtil.allDatePartern);
		Connection conn=Jsoup.connect(url);
		for(String key:head.keySet()){
			conn.header(key, head.get(key));		}
		
		Document doc2 = conn.get();
		Element body=doc2.body();
		return body;
	}
	
	public static Element postBody(String host,String url,Map<String,Object> params) throws IOException{
		String theDate=DateUtil.DateFormat(new Date(), DateUtil.allDatePartern);
		Connection conn = Jsoup.connect(url);
				
			conn.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
				.header("Accept-Encoding","gzip, deflate")
				.header("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3")
				.header("Cache-Control","max-age=0")
				.header("Connection","keep-alive")
				.header("Cookie","	_ga=GA1.2.945928992.1418376809; Hm_lvt_de86350f237ca0c45404683c676984a5=1418376810,1418377130,1418616003; bdshare_firstime=1418376821946; CNZZDATA2173831=cnzz_eid%3D840194547-1418372569-http%253A%252F%252Fph.156580.com%252F%26ntime%3D1418620861; AJSTAT_ok_times=3; ECM_ID=56700a1e5f8ef9442a5fe9569148ac40296c8cc5; Hm_lpvt_de86350f237ca0c45404683c676984a5=1418625066; 53kf_70746523_keyword=; kf_70746523_keyword_ok=1; onliner_zdfq70746523=0; _gat=1; AJSTAT_ok_pages=6; invite_53kf_totalnum_1=1; www_156580_com_index_php=1")
				.header("Host",host)
				.header("Referer","http://www.156580.com/user/login.html")
				.header("User-Agent","	Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
				for(int i=0;i<params.size();i++){
					for(String key:params.keySet()){
						conn.data(key,(String)params.get(key));
					}
				}
				Document doc2=conn.post();
		Element body=doc2.body();
		return body;
	}
}
