package cn.lvyjx.test.ddd;

import java.util.Queue;

import com.sun.jmx.remote.internal.ArrayQueue;

public class MainDemo {

	public static void main(String[] args) {
		Goods redWine = new Goods();
		redWine.setGoodsName("红酒");
		redWine.setGoodsPrice(1800d);
		redWine.setId(1);
		Goods chocolate = new Goods();
		chocolate.setGoodsName("巧克力");
		chocolate.setGoodsPrice(180d);
		chocolate.setId(2);
		
		Customer xiaoming = new Customer();
		xiaoming.setCustomerName("小明");
		xiaoming.setId(1);
		Customer ahong = new Customer();
		ahong.setCustomerName("阿红");
		ahong.setId(2);
		
		Cashier cashierMM = new Cashier();
		cashierMM.setCashierName("MM");
		cashierMM.setId(1);
		
		xiaoming.likeBuy(redWine);
		ahong.likeBuy(redWine);
		ahong.likeBuy(chocolate);
		
		ArrayQueue<Customer> customerQueue =  new ArrayQueue<Customer>(2);
		customerQueue.add(xiaoming);
		customerQueue.add(ahong);
		for(int i = 0 ; i < customerQueue.size(); i++){
			cashierMM.cashRegister(customerQueue.get(i));
		}
	}
}
