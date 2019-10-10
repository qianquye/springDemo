package cn.lvyjx.test.model.observer.myobserver;

public abstract class MyApplicationEvent {

	protected transient Object  source;
	
	public MyApplicationEvent(Object obj){
		source = obj;
	}
	
	

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}
	
}
