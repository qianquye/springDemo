package cn.lvyjx.test.hashmapDemo;

import java.util.Set;

public interface MyMap<K,V> {

	int size();
	
	boolean isEmpty();
	
	V get(Object key);
	
	V put(K key ,V value);
	
	V remove(Object key);
	
	Set<MyMap.MyEntry<K,V>> entrySet();
	
	interface MyEntry<K,V> {
		K getKey();
		V getValue();
		
		V setValue(V value);
	}
	
	
}

