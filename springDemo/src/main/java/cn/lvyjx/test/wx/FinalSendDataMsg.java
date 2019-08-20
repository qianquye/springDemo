package cn.lvyjx.test.wx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.TreeMap;

public class FinalSendDataMsg extends Thread{

	private String method = "";
	private WxMessVo wxMessVo = null;
	//发送模版消息url
	private static String sendMessURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?";
	public void run(){
		if(method.equals("")){
		//	SendMessDataMemAsyn(object);
		}else if(method.equals("sendMessDataUser")){
			sendMessDataUserAsyn(wxMessVo);
		}
	}
	
	
	
	
	
	/**
	 * 格式 ：
	 * {
	 *   "touser":"",
	 *   "template_id":"",
	 *   "data":{
	 *     "first":{"value":"","color":""},
	 *     "keyword1":{"value":"","color":""},
	 *     "keyword2":{"value":"","color":""},
	 *     "keyword3":{"value":"","color":""},
	 *     "keyword4":{"value":"","color":""},
	 *      "remark":{"value":"","color":""}
	 *   }
	 * }
	 * @param mess
	 * @return
	 */
	private static String formatSendData(WxMessVo mess){
		//默认key
		String toUserKey = "touser";
		String templateKey = "template_id";
		String urlKey = "url";
		String dataKey = "data";
		String valueKey = "value";
		String toUser = mess.getTouser();
		String templateId = mess.getTemplateId();
		String url = mess.getUrl();
		//模块要发送的数据
		TreeMap<String,String> data = mess.getDataMap();
		Iterator<String> it = data.keySet().iterator();
		StringBuffer bufStr = new StringBuffer();
		bufStr.append("{");
		bufStr.append(formatString(toUserKey)+":").append(formatString(toUser)+",");
		bufStr.append(formatString(templateKey)+":").append(formatString(templateId)+",");
		if(!"".equals(url)){
			bufStr.append(formatString(urlKey)+":").append(formatString(url)+",");
		}
		int cIndex = 0 ;
		bufStr.append(formatString(dataKey)+":{");
		while(it.hasNext()){
			String key = (String)it.next();
			String cValue = data.get(key);
			bufStr.append(formatString(key)+":{");
			bufStr.append(formatString(valueKey)+":");
			bufStr.append(cValue);
			bufStr.append("}");
			if(cIndex<data.size() -1){
				bufStr.append(",");
			}
			cIndex ++;
		}
		// data结束
		bufStr.append("}");
		// 
		bufStr.append("}");
		return bufStr.toString();
	}
	
	private static String formatString(String str) {
		String rStr = "\""+str+"\"";
		return rStr;
	}
	
	public String sendMessDataUser(WxMessVo mess,FinalSendDataMsg sendMsg) throws Exception{
			sendMsg.method = "sendMessDataUser";
			sendMsg.wxMessVo= mess;
			SendMsgByThread.getInstance().sendMothed(sendMsg,SendMsgByThread.IN);
			return "asyn";
	}
	
	private String sendMessDataUserAsyn(WxMessVo mess){
		String rStr = null;
		try{
			// TODO 获取微信公众号的token
			String token = "";
			rStr = sendMessData(mess,token);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rStr;
	}
	
	/**
	 * 发送微信模版消息
	 * @param messageData
	 * @param token
	 * @return
	 */
	private static String sendMessData(WxMessVo mess,String token){
		HttpURLConnection http = null;
		//格式化消息内容
		String messageData = formatSendData(mess);
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
