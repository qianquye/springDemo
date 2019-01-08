package cn.lvyjx.test.wx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;

public class FinalSendDataMsg extends Thread{

	private String method = "";
	private Object object = null;
	private static String sendMessURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?";
	public void run(){
		if(method.equals("")){
		//	SendMessDataMemAsyn(object);
		}else if(method.equals("")){
		//	SendMessDataUserAsyn(object);
		}
	}
	
	
	
	
	
	
	private static String formatSendData(Object object){
		//默认key
		String toUserKey = "touser";
		String templateKey = "template_id";
		String urlKey = "url";
		String dataKey = "data";
		String valueKey = "value";
		String toUser = "用户opendid";
		String templateId = "模块id";
		String url = "需要跳转的地址";
		//模块要发送的数据
		TreeMap<String,String> data = new TreeMap<String,String>();
		
		return null;
	}
	
	
	/**
	 * 发送微信模版消息
	 * @param messageData
	 * @param token
	 * @return
	 */
	private static String sendMessData(String messageData,String token){
		HttpURLConnection http = null;
		
		String rStr = "";
		try{
			String sendURL = sendMessURL+"access_token="+token;
			URL url = new URL(sendURL);
			http = (HttpURLConnection) url.openConnection();
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setUseCaches(false);
			http.setConnectTimeout(3000);
			http.setReadTimeout(4000);
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.connect();
			OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(),"utf-8");
			osw.write(messageData);
			osw.flush();
			osw.close();
			if(http.getResponseCode() == 200){
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(),"utf-8"));
				String inputLine ; 
				while((inputLine = in.readLine()) != null){
					rStr += inputLine;
				}
				in.close();
			}
		}catch(Exception e){
			System.out.println("err:"+e.getMessage());
		}finally{
			if(http != null )
				http.disconnect();
		}
		System.out.println("发送结果："+rStr);
		return rStr;
	}
}
