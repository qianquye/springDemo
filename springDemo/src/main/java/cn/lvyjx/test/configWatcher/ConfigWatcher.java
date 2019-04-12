package cn.lvyjx.test.configWatcher;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
/**
 * Java动态修改配置即时生效的方式WatchService
 * @author Administrator
 *
 */
public class ConfigWatcher {
	
	private static WatchService watchService;
	
	public void init(){
		try{
			watchService = FileSystems.getDefault().newWatchService();
			URL url = ConfigWatcher.class.getResource("/");
			Path path = Paths.get(url.toURI());
			path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_CREATE);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		/**
		 * 启动监控线程
		 */
		Thread watchThread = new Thread(new WatchThread());
		watchThread.setDaemon(true);
		watchThread.start();
		
		/**
		 * 注册关闭钩子
		 */
		Thread hook = new Thread(new Runnable(){
			
			public void run(){
				try {
					watchService.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		Runtime.getRuntime().addShutdownHook(hook);
	}
	
  public class WatchThread implements Runnable{

	@Override
	public void run() {
		while(true){
			//试获取监控池的变化，如果没有则地直等待
			WatchKey watchKey;
			try {
				watchKey = watchService.take();
				for(WatchEvent<?> event : watchKey.pollEvents()){
					String editFileName = event.context().toString();
					System.out.println("editFileName:"+editFileName);
					/**
					 * 重新加载配置文件
					 */
				}
				watchKey.reset(); //完成一次监控就需要重置监控器一次
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	  
  }

}
