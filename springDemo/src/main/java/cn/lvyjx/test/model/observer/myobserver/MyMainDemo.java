package cn.lvyjx.test.model.observer.myobserver;

public class MyMainDemo {

	public static void main(String[] args) {
		
		MyApplicationEventMulticaster multicaster = new MyApplicationEventMulticaster();
		multicaster.addEventListener(new BroadcastReceive1());
		multicaster.addEventListener(new BroadcastReceive2());
		
		multicaster.multicastEvent(new BroadcastEvent(new Object(), "username", "123456"));
	}
}
