package cn.lvyjx.test.hashmapDemo;

import java.util.Iterator;

public abstract class AbstractMyMap<K,V> implements MyMap<K, V> {

	public int size(){
		return entrySet().size();
	}
	
	/**
	 * 获取值
	 */
	public V get(Object key){
		Iterator<MyEntry<K,V>> i = entrySet().iterator();
		if(key == null){
			while(i.hasNext()){
				MyEntry<K,V> mye = i.next();
				if(mye.getKey() == null)
					return mye.getValue();
			}
		}else{
			while(i.hasNext()){
				MyEntry<K,V> mye = i.next();
				if(key.equals(mye.getKey()))
					return mye.getValue();
			}
		}
		return null;
	}
	
	public V put(K key,V value){
		throw new UnsupportedOperationException();
	}
	
}
