package cn.lvyjx.test.model.observer.myobserver;

public interface MyEventListener<E extends MyApplicationEvent> {
	
	void onApplication(MyApplicationEvent event);

}
