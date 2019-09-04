package cn.lvyjx.test.ddd;

public class Cashier {
	
	private Integer id;
	private String cashierName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCashierName() {
		return cashierName;
	}
	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}
	
	
	public void cashRegister(Customer customer){
		CashRegister cashRegister = new CashRegister();
		
		for(int i = 0 ; i < customer.goods.size(); i++){
			cashRegister.cashRegisters(customer.goods.get(i));
		}
		customer.listenAmount(cashRegister.showAmount());
	}
}
