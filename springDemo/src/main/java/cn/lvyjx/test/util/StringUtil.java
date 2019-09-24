package cn.lvyjx.test.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 下划线转驼峰
	 * @param line
	 * @param smallCamel
	 * @return
	 */
	public static String underline2Camel(String line,boolean smallCamel){
		if(line  == null || "".equals(line)){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
		Matcher matcher = pattern.matcher(line);
		while(matcher.find()){
			String word = matcher.group();
			System.out.println("word:"+word);
			sb.append(smallCamel && matcher.start() == 0? Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
			int index = word.lastIndexOf("_");
			if(index > 0){
				sb.append(word.substring(1,index).toLowerCase());
			}else{
				sb.append(word.substring(1).toLowerCase());
			}
		}
		return sb.toString();		
	}
	
	/**
	 * 驼峰转下划线
	 * @param line
	 * @return
	 */
	public static String camel2Underline(String line){
		if(line == null || "".equals(line)){
			return "";
		}
		boolean flag = true;
		int size = line.length();
		for(int i = 0 ; i < size && flag; i++){
			if(Character.isUpperCase(line.charAt(i)) || Character.isLowerCase(line.charAt(i))){
				line = String.valueOf(line.charAt(i)).toUpperCase().concat(line.substring(i+1));
				flag = false;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
		Matcher matcher = pattern.matcher(line);
		while(matcher.find()){
			String word = matcher.group();
			System.out.println("work："+word);
			sb.append(word.toLowerCase());
			System.out.println("matcher.end():"+matcher.end());
			sb.append(matcher.end() == line.length()?"":"_");
		}
		return sb.toString();
	}
	
	public static String getIpResultAddress(String ipArg) throws IOException{
		String resultStr = "";
		InputStream is = null;
		ByteArrayOutputStream outStream = null;
		try{
			URL url = new URL("http://whois.pconline.com.cn/ip.jsp?ip="+ipArg);
			HttpURLConnection connect = (HttpURLConnection)url.openConnection();
			connect.setConnectTimeout(3000);
			connect.setReadTimeout(3000);
			is = connect.getInputStream();
			outStream = new ByteArrayOutputStream();
			byte[] buff = new byte[1024];
			int rc = 0;
			while((rc = is.read(buff,0,256)) > 0 ){
				outStream.write(buff,0,rc);
			}
			byte[] b = outStream.toByteArray();
			connect.disconnect();
			resultStr = new String(b,"GBK");
			if(resultStr == null || resultStr.equals("")){
				resultStr = "";
			}
		}catch(Exception e){
			System.out.println("登录时获取ip地址报错："+e.getMessage());
		}finally{
			try{
				if(outStream != null)
					outStream.close();
			}catch(Exception e){
				System.out.println("获取ip地址关闭输出流报错"+e.getMessage());
			}
			
			try{
				if(is != null){
					is.close();
				}
			}catch(Exception e){
				System.out.println("获取ip地址关闭输入流报错"+e.getMessage());
			}
			
		}
		
		return resultStr;
	}
	
	public static void main(String[] args) {
		/*System.out
				.println(underline2Camel("_abcdefg_hijklnm_opq_rst_uvw_", true));
		
		System.out
		.println(camel2Underline("_abcdefgHijklnmOpqRstUvw_"));
		System.out
		.println(underline2Camel("abcdefgHijklnmOpqRstUvw",true));*/
		
		/*try {
			System.out.println(getIpResultAddress("125.40.56.219"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		Map<String,String>  params = new HashMap<String,String>();
		params.put("其它国家", "其它国家");
		if(params.containsValue("其它")){
			System.out.println("ffffff");
		}
	}
}
