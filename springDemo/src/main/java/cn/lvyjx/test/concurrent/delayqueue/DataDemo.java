package cn.lvyjx.test.concurrent.delayqueue;

public class DataDemo implements Runnable {

	int a = -1 ; 
	
	public DataDemo(int a){
		this.a = a;
	}
	
	@Override
	public void run() {
		System.out.println("超时....撤单.....");
	}

}
