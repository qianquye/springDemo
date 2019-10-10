package cn.lvyjx.test.model.observer.myobserver;

public class BroadcastReceive1 implements MyEventListener{

	@Override
	public void onApplication(MyApplicationEvent event) {
		System.out.println("接收者1");
		
	}

}
