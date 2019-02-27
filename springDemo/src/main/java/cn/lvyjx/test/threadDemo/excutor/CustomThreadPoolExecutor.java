package cn.lvyjx.test.threadDemo.excutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 
 * 
 * 
 * 非阻塞线程池
 * @author Administrator
  重点讲解： 
其中比较容易让人误解的是：corePoolSize，maximumPoolSize，workQueue之间关系。 

1.当线程池小于corePoolSize时，新提交任务将创建一个新线程执行任务，即使此时线程池中存在空闲线程。 
2.当线程池达到corePoolSize时，新提交任务将被放入workQueue中，等待线程池中任务调度执行 
3.当workQueue已满，且maximumPoolSize>corePoolSize时，新提交任务会创建新线程执行任务 
4.当提交任务数超过maximumPoolSize时，新提交任务由RejectedExecutionHandler处理 
5.当线程池中超过corePoolSize线程，空闲时间达到keepAliveTime时，关闭空闲线程 (超出corePoolSize数量的线程的保留时间。)
6.当设置allowCoreThreadTimeOut(true)时，线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭 
 * 
 * 
 注意：41以后提交的任务就不能正常处理了，因为，execute中提交到任务队列是用的offer方法，如下的方法
  public void execute(Runnable command) {
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
            reject(command);
    }
 这个方法是非阻塞的，所以就会交给CustomRejectedExecutionHandler 来处理，所以对于大数据量的任务来说，
 这种线程池，如果不设置队列长度会OOM，设置队列长度，会有任务得不到处理，接下来我们构建一个阻塞的自定义线程池 
 *
 *
 */
public class CustomThreadPoolExecutor {

	private ThreadPoolExecutor pool = null;
	
	/**
	 * 线程池初始化方法
	 * corePoolSize 核心线程池大小----10
	 * maximumPoolSize 最大线程池大小----30
	 * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit
	 * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES
	 * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(10)====10容量的阻塞队列 (构造函数不传大小会默认为（Integer.MAX_VALUE ）)
	 * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
	 * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时,
	 * 							即当提交第41个任务时(前面线程都没有执行完,此测试方法中用sleep(100)),
	 * 						          任务会交给RejectedExecutionHandler来处理
	 */
	public void init(){
		pool = new ThreadPoolExecutor(
				10,
				30,
				30,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(10),
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
		private AtomicInteger count = new AtomicInteger(0);
		
		@Override
		public Thread newThread(Runnable r){
			Thread t =  new Thread(r);
			String threadName = CustomThreadPoolExecutor.class.getSimpleName()+ count.addAndGet(1);
			System.out.println(threadName);
			t.setName(threadName);
			return t;
		}
	}
	
	private class CustomRejectedExecutionHandler implements RejectedExecutionHandler{

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			System.out.println("error .....................");
			
		}
	}
	
	public static void main(String[] args) {
		
		/**
		 * 核心线程数为30个，缓冲队列有10个的线程池，每个线程任务，执行时会先睡眠3秒，保证提交10个任务时
		 * 线程数目被占用完，再提交30个任务旱，阻塞队列被占用完了，这样提交第41个任务时，会交给
		 * CustomRejectedExecutionHandler 异常处理类来处理
		 */
		CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor();
		executor.init();
		ExecutorService pool = executor.getCustomThreadPoolExecutor();
		for(int i = 1; i < 100 ; i++){
			System.out.println(" 提交第"+i+"个任务！");
			pool.execute(new Runnable(){

				@Override
				public void run() {
					try{
						Thread.sleep(3000);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					System.out.println("running ------------------");
				}
				
			});
		}
		//executor.destory();
		try{
			Thread.sleep(10000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
