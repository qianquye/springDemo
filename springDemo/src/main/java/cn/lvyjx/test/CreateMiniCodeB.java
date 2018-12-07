package cn.lvyjx.test;

/**
 * 小程序码的生成
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;





import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;



public class CreateMiniCodeB {
	
	public static void main(String[] args) {
		CreateMiniCodeB b = new CreateMiniCodeB();
		try {
			b.getMiniCode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getMiniCode() throws Exception{
		String str = getToken();
		JSONObject object = JSON.parseObject(str);
		String token = (String)object.get("access_token");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("scene", 124563);
		getCode(token,params,"miniCode.png");
		
	}
	
	public String getCode(String token,Map<String,Object> params,String fileName) throws Exception{
		String xurlB = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+token;
		if(params == null)
			return null;
		Map<String,Object> map = new HashMap<String,Object>();
		//if(params.get("scene")!= null){
			map.put("scene", params.get("scene"));
		//}
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(xurlB);
		httpPost.addHeader(HTTP.CONTENT_TYPE,"application/json");
		String body = JSON.toJSONString(params);
		StringEntity entity ;
		
		entity = new StringEntity(body);
		entity.setContentType("image/png");
		httpPost.setEntity(entity);
		HttpResponse response;
		response = httpClient.execute(httpPost);
		
		InputStream inputStream = response.getEntity().getContent();
		
		String path = "E:/dev/minicode/"+fileName;
		File targetFile = new File(path);
		
		FileOutputStream out = new FileOutputStream(path);
		byte[] buffer = new byte[8192];
		int bytesRead = 0;
		while((bytesRead = inputStream.read(buffer,0,8192)) != -1){
			out.write(buffer,0,bytesRead);
		}
		out.close();
		return null;
	}
	
	public String getToken() throws Exception{
		//String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx2a10159bc0353f16&secret=1d28471e7c04611a475c734cd98bcfc1";
		String tokenUrl = "http://feedback.beiing.cn/feedback/GetOtherAccessToken?appid=wx2a10159bc0353f16&appsecret=1d28471e7c04611a475c734cd98bcfc1";
		URL url = new URL(tokenUrl);
		Integer connectionTimeOut = 3000;
		HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
		urlconn.setConnectTimeout(connectionTimeOut);
		urlconn.setReadTimeout(connectionTimeOut+800);
		urlconn.connect();
		InputStreamReader input = new InputStreamReader(urlconn.getInputStream(),"UTF-8");
		BufferedReader bufR = new BufferedReader(input);
		StringBuffer strb = new StringBuffer();
		String line;
		while((line = bufR.readLine()) != null){
			strb.append(line);
		}
		try{
			input.close();
			bufR.close();
			input = null;
			bufR = null;
		}catch(Exception e ){
			e.printStackTrace();
		}finally{
			/*input.close();
			bufR.close();
			input = null;
			bufR = null;*/
		}
		return strb.toString();
	}

}
