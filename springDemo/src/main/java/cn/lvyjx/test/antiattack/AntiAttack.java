package cn.lvyjx.test.antiattack;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

public class AntiAttack {

	private static int maxTimes = 100; //单位周期内最大的连接数
	private static int period = 5; //周期
	private static int blockSecond = 10; //放入到黑名单后，多少秒自动清空
	
	//账号记数
	private static ConcurrentHashMap<String,AtomicInteger> logMap = new ConcurrentHashMap<String, AtomicInteger>();
	//黑名单
	private static ConcurrentHashMap<String,String> blackMap = new ConcurrentHashMap<String,String>();
	
	private static Thread worker = null;
	
	static{
		worker = new Thread(new Runnable() {
			@Override
			public void run() {
				long lastClearBlackMapTime = System.currentTimeMillis();
				while(true){
					System.out.println("当前记录数："+logMap.toString());
					logMap.clear(); // 清理请求次数清零
					if((System.currentTimeMillis() - lastClearBlackMapTime) > 1000*blockSecond){
						System.out.println("当前黑名单记录数："+blackMap.toString());
						blackMap.clear();
						lastClearBlackMapTime = System.currentTimeMillis();
					}
					try{
						Thread.sleep(1000*period); //每个周期内清零，
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}
		},"anti-attack-thead");
		worker.setDaemon(true);
		worker.start();
	}
	
	public static void logRequest(String orgCode,String userCode){
	//	System.out.println(userCode);
		String key = orgCode+"-"+userCode;
		if(logMap.containsKey(key)){
			AtomicInteger times = logMap.get(key);
			if(times != null){
				times.addAndGet(1);
				if(times.get() > maxTimes){
					blackMap.put(key, key);
				}
			}
		}else{
			AtomicInteger times = new AtomicInteger(1);
			logMap.put(key, times);
			
		}
	}
	
	public static boolean isBlocked(String orgCode,String userCode){
		String key = orgCode+"-"+userCode;
		if(blackMap.containsKey(key)){
			return true;
		}
		return false;
	}
	
	
	public static void test(String name){
		System.out.println(name);
	}
	
	public static void main(String[] args) {
		final String orgCode = "xxy";
		final String[] userName = {"zcy","ly","clp","lpc","cxl","lcy"};
		final AntiAttack at = new AntiAttack();
		ExecutorService executor =  Executors.newFixedThreadPool(3);
		final CountDownLatch count = new CountDownLatch(3);
		//System.out.println(Math.round(Math.random()* 5));
		int i = 1; 
		while(i > 0){
			
			executor.execute(new Runnable(){
				int num = (int)Math.round(Math.random()* 5) ;
				@Override
				public void run() {
					if(AntiAttack.isBlocked(orgCode,userName[num])){
						//System.out.println("连接数过多");
						return;
					}else{
						System.out.println("logMap size="+AntiAttack.logMap.toString());
					}
					AntiAttack.logRequest(orgCode,userName[num]);
					count.countDown();
				}
			});
			
		}
		
		try {
			count.await();
			executor.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
