package cn.lvyjx.test.ddd;

public class CashRegister {
	
	private Integer id;
	private String cashRegisterNo;
	private Double totalAmount;
	
	public CashRegister(){
		this.totalAmount = 0d;
	}
	
	/**
	 * 收银
	 * @param goods
	 */
	public void cashRegisters(Goods goods){
		this.totalAmount += goods.getGoodsPrice();
	}
	
	/**
	 * 显示收银总额
	 * @return
	 */
	public Double showAmount(){
		return this.totalAmount;
	}
}
