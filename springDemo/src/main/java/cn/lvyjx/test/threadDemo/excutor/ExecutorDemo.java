package cn.lvyjx.test.threadDemo.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorDemo {

	public static void main(String[] args) {
		System.out.println("availableProcess:"+Runtime.getRuntime().availableProcessors());
		//cachedThreadPoolTest();
		FixedThreadPoolTest();
	   //scheduledTreadPoolTest2();
		//singleThreadExecutorTest();
		
	}

	/**
	 * 可缓存的线程池，只有当第一任务执行完之后才能去执行第二个，会复用第一个任务的线程
	 */
	private static void cachedThreadPoolTest() {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		
		for(int i = 0 ; i < 10 ; i++){
			final int index = i;
			try{
				Thread.sleep(index * 1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			cachedThreadPool.execute(new Runnable(){

				@Override
				public void run() {
				  System.out.println("thread name :"+Thread.currentThread().getName()+"  index = "+index);
					
				}
				
			});
		}
	}
	
	/**
	 * 定长线程池，可控制最大并发数，超出的线程会在队列中等待
	 */
	private static void FixedThreadPoolTest(){
		ExecutorService fixedPool = Executors.newFixedThreadPool(3);
		for(int i = 0 ; i < 1000 ; i++){
			final int index = i; 
			
			fixedPool.execute(new Runnable(){
				@Override
				public void run() {
					try{
						System.out.println("thread name :"+Thread.currentThread().getName()+"  index = "+index);
						Thread.sleep(2000);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	/**
	 * 定长线程池，定时任务执行
	 */
	private static void scheduledTreadPoolTest1(){
		ScheduledExecutorService schedulePool = Executors.newScheduledThreadPool(4);
		schedulePool.schedule(new Runnable(){

			@Override
			public void run() {
				System.out.println("delay 3 seconds");
				
			}
			
		}, 3, TimeUnit.SECONDS);
		
		schedulePool.shutdown();
	}
	/**
	 * 定长线程，周期性任务执行
	 */
	private static void scheduledTreadPoolTest2(){
		ScheduledExecutorService schuduledPool = Executors.newScheduledThreadPool(4);
		schuduledPool.scheduleAtFixedRate(new Runnable(){

			@Override
			public void run() {
				System.out.println("delay 1 seconds, and excute every 3 seconds");
			}
			
		}, 1,3,TimeUnit.SECONDS);
	}
	
	/**
	 * 单线程化的线程池，只会用唯一的工作线程来执行任务，保证所有的任务按照定的顺序（FIFO,LIFO,优先级）执行。
	 */
	public static void singleThreadExecutorTest(){
		ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
		for(int i = 0 ; i < 10; i++){
			final int index = i; 
			singleExecutor.execute(new Runnable(){

				@Override
				public void run() {
					try{
						System.out.println("thread name = "+Thread.currentThread().getName()+"  index= "+index);
						Thread.sleep(2000);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
				
			});
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
