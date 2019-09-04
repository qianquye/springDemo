package cn.lvyjx.test.concurrent.delayqueue;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DelayTest {

	public static void main(String[] args) {
		ItemQueueThread ith = ItemQueueThread.getInstance();
		ith.init();
		
		Random r = new Random();
		for(int i = 0 ; i < 5; i++){
			int a = r.nextInt(20);
			System.out.println("预先等待时间："+a);
			DataDemo dd = new DataDemo(a);
			ith.put(a, dd, TimeUnit.SECONDS);
		}
	}
}
