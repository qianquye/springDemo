package cn.lvyjx.test.util;

import java.io.IOException;

public class WorkdayUtil {
	private static final String url = "http://api.k780.com/";
	private static final String cmd = "app=life.workday";
	private static final String appkey = "45252";
	private static final String sign = "120031f495d3bc27105d250639053b8b";
	
	public static void main(String[] args) {
		try {
			checkDate("2019-09-24");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int checkDate(String datestr) throws Exception{
		int flag = 1;
		
		flag = queryDate(datestr);
		
		return flag;
	}

	private static int queryDate(String datestr) {
		int flag = 1; 
		try{
			String resMessage = getWorkdayMsg(datestr);
			System.out.println("resmessage: "+resMessage);
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
		
		
	}

	private static String getWorkdayMsg(String datestr) throws IOException {
		String sendUrl = url;
		String params = cmd +"&date="+datestr+"&appkey="+appkey+"&sign="+sign+"&format=json";
		String resMessage = HttpUtil.sendGet(sendUrl, params);
		return resMessage;
	}
}
