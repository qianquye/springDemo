package cn.lvyjx.test;

import java.util.HashMap;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

public class TestDemo {

	public static String token = "efafdadsafdsdf";
	
	public static void main(String[] args) {
		
		/*String path = "cn.lvyjx.test.ClassInstance cn.lvyjx.test.ClassInstance1;cn.lvyjx.test.ClassInstance2\t\ncn.lvyjx.test.ClassInstance3";
		String[] paths = StringUtils.tokenizeToStringArray(path, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
		for(String p : paths){
			System.out.println("p="+p);
		}
		System.out.println("token before:"+token);
		TestDemo.token = "123343556746";
		System.out.println("token before:"+token);
		
		String className ="cn.lvyjx.test.ClassInstance";
		
		try {
			Class clazz = Class.forName(className);
			System.out.println("clazz:"+clazz.getName());
			ClassInstance intance = (ClassInstance)clazz.newInstance();
			intance.test();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}*/
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(null,"123456");
		String key ="sss";
		int h;
		System.out.println("key>"+((h = key.hashCode()) ^ (h >>> 16)));
	}
}
