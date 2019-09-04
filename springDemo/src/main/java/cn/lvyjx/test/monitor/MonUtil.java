package cn.lvyjx.test.monitor;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import cn.lvyjx.test.wx.WxMessVo;
import cn.lvyjx.test.wx.WxSendDataMess;

public class MonUtil {

	public static BufferedWriter bufferedWriter = null;
	public static String catalinaBase = null;
	public static String errFilePath = null;
	public static String fileSeparator = System.getProperties().getProperty("file.separator","/");
	
	static{
		catalinaBase = System.getProperties().getProperty("catalina.base",null);
		if(catalinaBase != null){
			errFilePath = catalinaBase+fileSeparator+"logs"+fileSeparator+"mon.err";
		}
	}
	
	public MonUtil(){};
	
	public static void logErr(final Throwable t){
		if(MonUtil.ignoreThrowable(t) == true){
			return;
		}
		String url = null;
		try{
			url = AllCodeFilter.request.get().getRequestURL().toString();
		}catch(Exception e){
			url = "";
		}
		final String reqUrl = url;
		MonUtil.logErrFile(t);
		String ntfUrl = "http://127.0.0.1/monitor/e.jsp";
		try{
			String host = AllCodeFilter.request.get().getHeader("Host");
			int pos = url.indexOf(host)+host.length();
			ntfUrl = url.substring(0,pos)+"/monitor/e.jsp";
		}catch(Exception e){}
		final String wxNtfUrl = ntfUrl;
		Thread worker = new Thread(new Runnable(){
			public void run(){
				Throwable cause = t.getCause() == null ? t: t.getCause();
				String message = t.getClass().getCanonicalName()+":"+cause.getMessage();
				StackTraceElement[] trace = cause.getStackTrace();
				if(trace != null && trace.length > 0){
					message += "  "+trace[0].toString();
				}
				MonUtil.sendWxAtlerNofity(formatCalendar("yyyy-MM-dd HH:mm:ss")+":"+"异常"+reqUrl,message,wxNtfUrl);
			}
		});
		worker.setDaemon(true); //设为守护线程，不然关不了
		worker.start();
	}
	
	public static void sendWxAtlerNofity(String title,String message,String url) {
		try{
			if(title == null){
				title = "标题空";
			}
			if(message == null){
				message = "信息为空";
			}
			if(url == null){
				url = "";
			}
			Integer hour = Integer.parseInt(formatCalendar("HH"));
			if(hour > 21 || hour < 8){
				return ;
			}
			//list 获取推送微信
			List<String> openIdList = new ArrayList();
			for(int i = 0; i < openIdList.size(); i++ ){
				WxMessVo mess = new WxMessVo();
				mess.setTouser(openIdList.get(i));
				mess.setTemplateId(""); //对应的模板id
				mess.setUrl(url);
				TreeMap<String,String> dataMap = new TreeMap<String,String>();
				dataMap.put("first", title);
				dataMap.put("content",message);
				dataMap.put("occurtime",formatCalendar("yyyy-MM-dd HH:mm:ss"));
				dataMap.put("remark", "请点击查看详细内容");
				mess.setDataMap(dataMap);
				WxSendDataMess.sendWxMessToUser(mess);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private synchronized static void logErrFile(Throwable t) {
		PrintStream ps = null;
		String url = null;
		String referer = null;
		String userAgent = null;
		
		try{
			url = AllCodeFilter.request.get().getRequestURL().toString();
			referer = AllCodeFilter.request.get().getHeader("Referer");
			userAgent = AllCodeFilter.request.get().getHeader("User-Agent");
		}catch(Exception e){
			url = "";
		}
		try{
			FileOutputStream fos = new FileOutputStream(errFilePath,true);
			ps = new PrintStream(fos,false,"utf-8");
			ps.append(formatCalendar("yyyy-MM-dd HH:mm:ss")+url+" \n");
			ps.append("Refere: "+referer+" \n");
			ps.append("User-Agent: "+userAgent +" \n");
			printRequestParam(AllCodeFilter.request.get(),ps);
			ps.append("\n");
			t.printStackTrace(ps);
			ps.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(ps != null){
					ps.close();
				}
			}catch(Exception e){}
		}
	}
	
	private static void printRequestParam(HttpServletRequest request,PrintStream ps){
		try{
			if(request != null){
				return ;
			}
			Enumeration e = request.getParameterNames();
			while(e.hasMoreElements()){
				String key = e.nextElement().toString();
				if(key == null){
					continue;
				}
				ps.append(key+":"+request.getParameter(key)+"\n");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String formatCalendar(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(Calendar.getInstance().getTime());
	}

	protected static boolean ignoreThrowable(Throwable t){
		if(t == null ) return true;
		if(t instanceof java.io.IOException) {
			return true;
		}/*else if(t instanceof org.apache.catalina.connector.ClientAbortException){
			return true;
		}*/
		if(t instanceof java.lang.IllegalStateException){
			String msg = t.getMessage();
			if(msg != null && msg.indexOf("after the response has been committed") >= 0){
				return true;
			}
		}
		/*if(t instanceof org.apache.jasper.JasperException){
			String msg = t.getMessage();
			if(msg != null && msg.indexOf("getOutputStream() has already bean called for this response") >= 0 ){
				return true;
			}
		}*/
		if(t instanceof SQLException){
			String msg = t.getMessage();
			if(msg != null && msg.indexOf("Schema overflow") >= 0){
				return true;
			}else if(msg != null && msg.indexOf("Lock wait timeout exceeded") >= 0){
				return true;
			}
		}
		return false;
		
	}
	
}
