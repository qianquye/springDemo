package cn.lvyjx.test.ddd;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	
	private Integer id;
	
	public String customerName;
	
	public List<Goods> goods = new ArrayList<Goods>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public List<Goods> getGoods() {
		return goods;
	}

	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}
	
	/**
	 * 顾客选购商品
	 * @param goods
	 */
	public void likeBuy(Goods goods) {
		this.goods.add(goods);
	}
	
	/**
	 * 应收金额
	 * @param amount
	 */
	public void listenAmount(Double amount){
		System.out.println("我是"+this.getCustomerName()+",购买了"+goods.size()+"件商品"+"共花了"+amount+"元");
	}

}
