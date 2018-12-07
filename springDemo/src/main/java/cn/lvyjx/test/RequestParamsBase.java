package cn.lvyjx.test;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

public class RequestParamsBase {
	ThreadLocal<String> curDataBase = new ThreadLocal<String>();
	private static Map<String,String> params = new HashMap<String,String>();
	{
		
		params.put("lovcg.com","lovcg");
		params.put("lovcg1.com","lovcg1");
		params.put("lovcg2.com","lovcg2");
		params.put("lovcg3.com","lovcg3");
		params.put("lovcg4.com","lovcg4");
	}
	
	
	
	public String getCurSchema(HttpServletRequest request) throws NoSuchElementException{
		
		String schema = (String) request.getSession().getAttribute("curSchema");
		if(schema != null){
			return schema;
		}
		try{
			schema = getCurSchema(request);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(schema == null || schema.trim().length() == 0){
			throw new NoSuchElementException(); //noSuchSchemaException
		}
		request.getSession(true).setAttribute("curSchema", schema);
		return schema;
	}
	
	public String getSchemaByRequestUrl(HttpServletRequest request) throws NoSuchElementException{
		//http://lovcg.cn:8080/dd/ee
		String url = request.getRequestURL().toString().trim().toLowerCase();
		
		
		String curURL = "";
		try{
			if(url.startsWith("http://")){
				url = url.substring("http://".length());
			}
			if(url.startsWith("https://")){
				url = url.substring("https://".length());
			}
			if(url.indexOf(":") >= 0){
				url = url.substring(0,url.indexOf(":"));
			}
			if(url.indexOf("//") >= 0){
				url = url.substring(0,url.indexOf("//"));
			}
			curURL = getDomainManage(url);
			if("".equals(curURL)){
				throw new NoSuchElementException();
			}
		}catch(Exception e){
			throw new NoSuchElementException();
		}
		return curURL;
	}
	
	/**
	 * 域名解析
	 * @param domainName
	 * @return
	 * @throws Exception
	 */
	public String getDomainManage(String domainName) throws Exception{
		if(domainName ==null) 
			return "";
		String curDomainName = domainName.trim();
		int curNum = domainName.indexOf("/");
		if(curNum > 0){
			curDomainName = curDomainName.substring(0,curNum);
		}
		//通过域名获取 从数据库里找域名对应的数据库名
		String rValue = getValue(curDomainName);
		return rValue;
	}
	
	public String getValue(String key){
		return params.get(key);
	}
}
