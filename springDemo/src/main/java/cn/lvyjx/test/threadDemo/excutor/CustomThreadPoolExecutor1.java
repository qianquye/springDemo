package cn.lvyjx.test.threadDemo.excutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 阻塞线程池
 * @author Administrator
 *  当提交任务被拒绝时，进入拒绝机制，我们实现拒绝方法，把任务重新用阻塞提交方法put提交，
 *   实现阻塞提交任务功能，防止队列过大，OOM，提交被拒绝方法在下面 
 *   public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();
        int c = ctl.get();
        if (workerCountOf(c) < corePoolSize) {
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (! isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
        else if (!addWorker(command, false))
            // 进入拒绝机制， 我们把runnable任务拿出来，重新用阻塞操作put，来实现提交阻塞功能
            reject(command);
    }
 */
public class CustomThreadPoolExecutor1 {

	public ThreadPoolExecutor pool = null;
	
	public void init(){
		pool = new ThreadPoolExecutor(1,3,3,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(5),
				new CustomThreadFactory(),
				new CustomRejectedExecutionHandler());
	}
	
	public void destory(){
		if(pool != null){
			pool.shutdown();
		}
	}
	
	public ExecutorService getCustomThreadPoolExecutor(){
		return this.pool;
	}
	
	private class CustomThreadFactory implements ThreadFactory{

		private AtomicInteger count = new  AtomicInteger(0);
		@Override
		public Thread newThread(Runnable r) {
			Thread thread = new Thread(r);
			String threadName = CustomThreadPoolExecutor1.class.getSimpleName()+count.addAndGet(1);
			System.out.println(threadName);
			thread.setName(threadName);
			return thread;
		}
	}
	
	private class CustomRejectedExecutionHandler implements RejectedExecutionHandler{

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			try{
				//核心发造点，由blockingqueue的offer改成put阻塞方法
				executor.getQueue().put(r);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args) {
		CustomThreadPoolExecutor1 exec = new CustomThreadPoolExecutor1();
		exec.init();
		ExecutorService pool = exec.getCustomThreadPoolExecutor();
		for(int i = 1; i < 100; i++){
			System.out.println("提交第"+i+"个任务");
			pool.execute(new Runnable(){
				@Override
				public void run(){
					try{
						System.out.println(">>>task is running........");
						TimeUnit.SECONDS.sleep(10);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			});
		}
		
		//exec.destory();
		
		try{
			Thread.sleep(10000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
