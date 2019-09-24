package cn.lvyjx.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;



public class HttpUtil {
	
	/**
	 * 
	 * @param url
	 * @param charset
	 * @param parameters
	 * @return
	 */
	public static String sendPost(String url,String charset,List<NameValuePair> parameters){
		try{
			HttpClient client = new HttpClient();
			PostMethod postMethod = new PostMethod(url);
			postMethod.addParameter("Connection","Keep-Alive");
			postMethod.addParameter("Charset",charset);
			postMethod.addParameter("Content-type","application/x-www-form-urlencoded");
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,charset);
			
			if(parameters != null){
				NameValuePair[] pair = new NameValuePair[parameters.size()];
				for(int i = 0 ; i < parameters.size(); i++){
					pair[i] = new NameValuePair(parameters.get(i).getName(),parameters.get(i).getValue());
				}
				postMethod.setRequestBody(pair);
			}
			int statusCode = client.executeMethod(postMethod);
			if(statusCode != HttpStatus.SC_OK){
				System.out.println("method failed: "+postMethod.getStatusLine());
			}
			
			byte[] responseBody = postMethod.getResponseBody();
			String res = new String(postMethod.getResponseBody(),charset);
			
			System.out.println(res);
			postMethod.releaseConnection();
			return res;
		}catch(Exception ex){
			System.out.println(url+" Post "+ex.getMessage());
			return "";
		}
	}

	/**
	 * 指定url发送get方法请求
	 * @param url
	 * @param params 请求参数 name=value1&name2=value2格式 
	 */
	public static String sendGet(String url,String params) throws IOException{
		String result = "";
		BufferedReader in = null;
		try{
			String urlNameString = url +"?"+params;
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			//设置通用的请求属性
			connection.setRequestProperty("accept","*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozill/4.0(compatible;MSIE 6.0; Windows NT 5.1;sV1)");
			
			//建立实际的连接
			connection.connect();
			Map<String,List<String>> map = connection.getHeaderFields();
			for(String key : map.keySet()){
				System.out.println(key+"-->"+map.get(key));
			}
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line ;
			while((line = in.readLine()) != null){
				result += line;
			}
		}catch(Exception e){
			throw e;
		}finally{
			try{
				if(in != null){
					in.close();
				}
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		return result;
	}
}
