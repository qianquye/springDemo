package cn.lvyjx.test.structure;

import java.util.ArrayList;
import java.util.Vector;

public class ChilderDemo implements FatherDemo{


	public void test(){
		FatherDemo.println();
		Vector v = new Vector();
		ArrayList list = new ArrayList();
		
		String key = "ss";
		System.out.println(key.hashCode());
		System.out.println(5 & key.hashCode());
	}
	
	public static void main(String[] args) {
		String key = "name";
		String key1 = "age";
		System.out.println(key.hashCode());
		System.out.println(5 & hash(key));
		System.out.println(key1.hashCode());
		System.out.println(5 & hash(key1));
	}

	static final int hash(Object key){
		int h;
		return (key == null) ? 0 :(h = key.hashCode()) ^ (h >>> 16);
	}
}
