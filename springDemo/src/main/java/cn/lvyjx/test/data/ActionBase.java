package cn.lvyjx.test.data;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionBase {

	private HttpServletResponse response;
	private HttpServletRequest request;
	
	public void preExecute() throws Exception{
		CurDataSource ds = new CurDataSource();
		
		String schema = (String) request.getSession(true).getAttribute("schema");
		if(schema == null){
			schema = getParameter("schema",null);
		}
		ds.curSchema.set(schema);
		
		initSchema(request);
		
		logBeforeExecute();
	}
	
	public void logBeforeExecute(){
		CurDataSource ds = new CurDataSource();
		String curSchema = ds.curSchema.get();
		if(curSchema == null){
			curSchema = "";
		}
		//日记记录
		String msg = "--request the url:"+request.getRequestURL()+" org:"+curSchema+" actionName:"+this.getClass().getName();
	}
	
	public void printRequestParam(HttpServletRequest request){
		String url = (request.getRequestURL().toString());
		System.out.println("---------start-----------");
		try{
			if(request == null){
				return ;
			}
			Enumeration e = request.getParameterNames();
			while(e.hasMoreElements()){
				String key = e.nextElement().toString();
				if(key == null){
					continue;
				}
				System.out.println(key+":"+request.getParameter(key));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("---------start-----------");
	}
	
	
   public String initSchema(HttpServletRequest request) throws Exception{
	   String schema = getCurSchema(request);
	   CurDataSource ds = new CurDataSource();
	   ds.curSchema.set(schema);
	   return schema;
   }
	
   public String getCurSchema(HttpServletRequest request) throws Exception{
	   String schema = (String) request.getSession(true).getAttribute("schema");
		if(schema != null){
			return schema;
		}
		
		try{
			schema = getSchemaByRequestUrl(request);
		}catch(Exception e){
			schema = getParameter(request,"schema",null);
		}
		if(schema == null || schema.trim().length() == 0){
			throw new Exception("no such schema");
		}
		request.getSession(true).setAttribute("schema", schema);
		return schema;
   }
   
   public String getParameter(HttpServletRequest request,String paramName,String substitution) {
	   String value = getParameter(request,paramName);
	   if(value == null){
		   return substitution;
	   }
	   return value;
   }
   
   public String getSchemaByRequestUrl(HttpServletRequest request) throws Exception{
	   String url = request.getRequestURL().toString().trim().toLowerCase();
	   String webURL = ".com";
	   if(url.indexOf(webURL) < 0){
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
				   throw new Exception("no such schema");
			   }
			   return curURL;
		   }catch(Exception e){
			   throw new Exception("no such schema");
		   }
	   }else{
		   try {
			if(url.startsWith("http://")){
				   url = url.substring("http://".length());
			   }
			   if(url.startsWith("https://")){
				   url = url.substring("https://".length());
			   }
				return  url.substring(0,url.indexOf("."));
			} catch (Exception e) {
				e.printStackTrace();
			}
	   }
	   return null;
   }
   
   
   public String getDomainManage(String domainName) throws Exception{
	   int curNum = domainName.indexOf("/");
	   if(curNum > 0){
		   domainName =domainName.substring(0,curNum);
	   }
	   //查询验证是否存在 TODO
	   String rValue = "";
	   return rValue;
	   
   }
   
	public String getParameter(String paraName,String substitution) {
		String value = getParameter(paraName);
		if(value == null){
			return substitution;
		}
		return value;
	}
	
	public String getParameter(String paramName){
		return getParameter(request,paramName);
	}
	
	public String getParameter(HttpServletRequest request,String paramName){
		String value = "";
		try{
			value = request.getParameter(paramName);
			if(value == null){
				Object obj = request.getAttribute(paramName);
				if(obj instanceof Date){
					value = (String) obj;
				}
			}
		}catch(Exception e){
			value = "";
		}
		return value;
	}
}
