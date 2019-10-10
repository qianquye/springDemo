package cn.lvyjx.test.model.observer.myobserver;

public class BroadcastEvent extends MyApplicationEvent{

	private String name;
	private String pwd;
	
	public BroadcastEvent(Object obj,String name,String pwd) {
		super(obj);
		this.name = name;
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	
}
