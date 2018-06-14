package com.example.demo.utils;

import java.net.URLEncoder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsoupUtil {
	// 根据传入的url获取该路径下的html
	private static Logger logger=LoggerFactory.getLogger(JsoupUtil.class);
	public static Document getDocument(String url) {
		Document doc = null;
		while(true) {
			try {
				URLEncoder.encode(url, "UTF-8");
				
				Connection con = Jsoup.connect(url);
				con.header("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
				con.header("Accept-Language", "zh-CN,zh;q=0.9");
				con.header("Connection", "keep-alive");
				con.header("Accept-Encoding", "gzip, deflate");
				Thread.sleep(1000);
				
				doc = con.timeout(10000).get();
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(8000);
					continue;
				} catch (InterruptedException e1) {
					logger.error(e.toString());
				}
				logger.error(e.toString());
			}
		}
		return doc;
	}

}
