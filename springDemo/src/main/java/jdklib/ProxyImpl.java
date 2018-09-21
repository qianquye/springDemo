package jdklib;

import java.util.HashMap;
import java.util.Map;

public class ProxyImpl implements ProxyInterface {

	public final Map<String,Object> data = new HashMap<String,Object>();
	
	public void save() {
		System.out.println("保存数据");

	}
   public void setData(String key ,Object value){
	   data.put(key, value);
   }
   public Object getData(String key){
	   return data.get(key);
   }
}
