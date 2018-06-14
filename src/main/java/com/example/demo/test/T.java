package com.example.demo.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class T {
//	public static void main(String[] args) {
//		String s1="http://www.52solution.com:80/upload/20180111/5a5712ab4c961.png";
//		//http://cdn.huodongxing.com/file/20150620/111A834D43062A4DAD59BFAD793BAE963A/30152979906200108.jpg?auth_key=1524093473-0-0-ffc4363c253b35db8bd84ec037d36321?auth_key=1524320399-0-0-f3d52f99927bcd34a56ed9d2af0397ac?auth_key=1524322197-0-0-c5d422e88ce20f9920b9034fe8d08fcf?auth_key=1524577679-0-0-5d560d274fb217c1a93b996e91c5d5d5
//		// s1="https://mmbiz.qpic.cn/mmbiz_png/ap2EGaHd4KuxjLJ2CtzsJibvaiboUFHuYic72TMe5lrThdrr11nr7UBLw92VAFYqHB1nF6HsUsXZ5wT2tmOyLVl2g/640";
//		int i = getCharacterPosition(s1);
//		System.out.println(i);
//		int indexOf = s1.indexOf("?");
//		System.out.println(indexOf);
//		if(indexOf==-1) {
//			indexOf=s1.length();
//		}
//		String sub = s1.substring(i, indexOf);
//		System.out.println(sub);
//		
//	}
	public static int getCharacterPosition(String string){  
	    //这里是获取"/"符号的位置  
	   Matcher matcher = Pattern.compile("/").matcher(string);  
	    int mIdx = 0;  
	    while(matcher.find()) {  
	       mIdx++;  
	       //当"/"符号第三次出现的位置  
	       if(mIdx == 3){  
	          break;  
	       }  
	    }  
	    return matcher.start();  
	 }  
	/**
	 * 下载文件
	 * 
	 * @param urlStr
	 *            文件地址
	 * @param savePath
	 *            保存路径
	 */
	public static void downLoad(String urlStr, String savePath) throws IOException {
		URL url = new URL(urlStr);
		File file = new File(savePath);
		File dir = file.getParentFile().getAbsoluteFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(3000);
		InputStream inputStream = connection.getInputStream();
		FileOutputStream outputStream = new FileOutputStream(savePath);
		byte[] buffer = new byte[1204];
		int byteread;
		while ((byteread = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, byteread);
		}
		System.out.println("******************下载完毕********************");
		return;
	}
}
