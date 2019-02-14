package cn.lvyjx.test.hashmapDemo;

import java.io.Serializable;
import java.util.Set;

public class MyHashMap<K,V> extends AbstractMyMap<K, V> implements MyMap<K,V>,Cloneable,Serializable{

	private static final long serialVersionUID = -1763534430442427142L;
	transient MyNode<K,V>[] table;
	int threshold;
	float loadFactor;
	static final int DEAFAULT_INITIAL_CAPACITY = 1 << 4 ; //AKA 16
	static final int MAXIMUM_CAPACITY = 1 << 30;
	static final float DEFAULT_LOAD_FACTOR = 0.75f;
	
	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<MyMap.MyEntry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	public V put(K key ,V value){
		return putVal(hash(key),key,value,false,true);
	}

	private V putVal(Object hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
		MyNode<K,V>[] tab ; MyNode<K,V> p ; int n,i;
		if((tab = table) == null || (n = tab.length) ==0){
			n = (tab = resize()).length;
		}
		return null;
	}

	final MyNode<K, V>[] resize() {
		MyNode<K,V>[] oldTab = table;
		int oldCap = (oldTab == null) ? 0 : oldTab.length;
		int oldThr = threshold;
		int newCap,newThr = 0;
		if(oldCap > 0){
			if(oldCap >= MAXIMUM_CAPACITY){
				threshold = Integer.MAX_VALUE;
				return oldTab;
			}else if((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEAFAULT_INITIAL_CAPACITY){
				newThr = oldThr << 1;
			}else if(oldThr > 0){
				newCap = oldThr;
			}else {
				newCap = DEAFAULT_INITIAL_CAPACITY;
				newThr = (int)(DEFAULT_LOAD_FACTOR * DEAFAULT_INITIAL_CAPACITY);
			}
			if(newThr == 0){
				float ft = (float)newCap * loadFactor;
				newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ? (int)ft : Integer.MAX_VALUE);
			}
			threshold =  newThr;
			MyNode<K,V>[] newTab = (MyNode<K,V>[]) new MyNode[newCap];
			table = newTab;
			if(oldTab != null){
				for(int j = 0 ; j < oldCap; ++j){
					MyNode<K,V> e;
					if((e = oldTab[j]) != null){
						oldTab[j] = null;
						if(e.next == null){
							newTab[e.hash & (newCap - 1)] = e;
						}
					}
				}
			}
		}
		return null;
	}

	private Object hash(K key) {
		int h;
		return (key == null) ? 0:(h = key.hashCode()) ^ (h >>> 16);
	}
	
	final float loadFactor() { return loadFactor; }
	
	static class MyNode<K,V> implements MyMap.MyEntry<K, V>{
		final int hash;
		final K key;
		V value;
		MyNode<K,V> next;
		
		MyNode(int hash,K key, V value,MyNode<K,V> next){
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}
		@Override
		public final K getKey() {
			return key;
		}

		@Override
		public final V getValue() {
			return value;
		}
		
		public final String toString(){
			return key+"="+value;
		}

		@Override
		public V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}
		
	}
	
	
}
