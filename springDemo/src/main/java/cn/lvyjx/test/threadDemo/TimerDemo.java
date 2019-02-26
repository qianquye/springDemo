package cn.lvyjx.test.threadDemo;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定时处理任务
 * @author Administrator
 *
 */
public class TimerDemo {

	public ThreadPoolExecutor poolExecutor = null;
	
	public void init(){
		//poolExecutor = new ThreadPoolExecutor(10,20,5000,TimeUnit.MICROSECONDS,new ArrayBlockingQueue<Runnable>());
	}
	
	public static void checkTask(){
		
		
	}
}
  
