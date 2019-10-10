package cn.lvyjx.test.model.observer.myobserver;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyApplicationEventMulticaster {
	
	public final Set<MyEventListener<?>> myListeners;
	public  Executor excutor ;
	public MyApplicationEventMulticaster(){
		myListeners = new LinkedHashSet<>();
		excutor = Executors.newCachedThreadPool();
	}
	/**
	 * 监听者
	 * @param event
	 */
	public void addEventListener(MyEventListener eventListener){
		myListeners.add(eventListener);
		
	}
	
	public void multicastEvent(MyApplicationEvent event){
		if(myListeners.isEmpty()){
			System.out.println("没有监听者");
			return ;
		}
		Iterator<MyEventListener<?>> listeners = myListeners.iterator();
		while(listeners.hasNext()){
			Executor excutor = getTaskExecutor();
			MyEventListener<?>  listener = listeners.next();
			excutor.execute(() -> invokeListener(listener,event));
			
		}
	}
	
	
	private void invokeListener(MyEventListener<?> listener,MyApplicationEvent event) {
		doInvokeListener(listener,event);
	}
	
	
	private void doInvokeListener(MyEventListener<?> listener,MyApplicationEvent event) {
		listener.onApplication(event);
	}
	public Executor getTaskExecutor(){
		return excutor;
	}
}
