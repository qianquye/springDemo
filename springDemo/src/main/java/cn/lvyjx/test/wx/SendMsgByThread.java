package cn.lvyjx.test.wx;

import java.util.ArrayList;
import java.util.List;

/**
 * 用一个静态线程做发送功能 
 * 好处:当需要做多个发送任务时，避免每次启动一个线程的资源消耗
 * @author Administrator
 *
 */
public class SendMsgByThread {

	private static SendMsgByThread sendMsgByThread = null;
	private static List<FinalSendDataMsg> sendList = new ArrayList<FinalSendDataMsg>();
	private static ThreadInstance ti = new ThreadInstance();
	public static final int IN = 1;
	public static final int OUT = 2;
	
	private SendMsgByThread(){
		init();
	}
	
	private void init(){
		System.out.println("start thread ....");
		ti.start();
	}
	
	/**
	 * 软性中断
	 */
	public synchronized void stopThread(){
		ti.isRun = false;
		ti.threadNotif();
	}
	
	/**
	 * 硬性中断
	 */
	public void stopThreadByInterrupted(){
		try{
			ti.isRun = false;
			ti.threadNotif();
			if(!ti.isInterrupted()){
				ti.interrupt();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 *  获取对象实例
	 * @return
	 */
	public synchronized static SendMsgByThread getInstance(){
		if(sendMsgByThread == null){
			sendMsgByThread = new SendMsgByThread();
		}
		return sendMsgByThread;
	}
	
	/**
	 * 调用发送方法
	 * @param sendDataMsg
	 * @param inOrOut
	 * @return
	 */
	public synchronized FinalSendDataMsg sendMothed(FinalSendDataMsg sendDataMsg,int inOrOut){
		if(inOrOut == IN){
			sendList.add(sendDataMsg);
			try{
				ti.threadNotif();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(inOrOut == OUT){
			if(sendList.size() > 0){
				FinalSendDataMsg sd = sendList.get(0);
				sendList.remove(0);
				return sd;
			}else{
				return null;
			}
		}
		return null;
	}
	
	static class ThreadInstance extends Thread{
		boolean isRun = true;
		public void run(){
			try{
				while(isRun){
					FinalSendDataMsg sd = getInstance().sendMothed(null, OUT);
					while (sd != null){
						sd.run();
						sd = getInstance().sendMothed(null, OUT);
					}
					synchronized (this) {
						this.wait();
						
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		synchronized void threadNotif(){
			this.notifyAll();
		}
	}
}
