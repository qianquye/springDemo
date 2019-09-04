package cn.lvyjx.test.concurrent.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class DelayItem<T extends Runnable> implements Delayed {

	private long time; //到期时间
	private T task;   //任务对象
	
	private static AtomicLong atomic = new AtomicLong(0); // 原子类
	private long n;
	
	public DelayItem(long timeout,T t){
		this.time = System.nanoTime() + timeout;
		this.task = t;
		this.n = atomic.getAndIncrement();
	}
	
	public T getTask(){
		return this.task;
	}
	
	@Override
	public int hashCode() {
		return task.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DelayItem){
			return obj.hashCode() == hashCode() ? true : false;
		}
		return false;
	}

	/**
	 * 给任务排序，到期时间短的排在前面
	 */
	@Override
	public int compareTo(Delayed o) {
		if(o == this)
		  return 0;
		if(o instanceof DelayItem){
			DelayItem<?> x = (DelayItem<?>)o;
			long diff = time - x.time;
			if(diff < 0){
				return -1;
			}else if(diff > 0){
				return 1;
			}else if(n < x.n){
				return -1;
			}else{
				return 1;
			}
		}
		long d = (getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS));
		return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
	}

	/**
	 * 返回与此对象相关的剩余延迟时间 ，以给定的时间单位表示
	 * 如果小于0，表示到期
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.time - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

}
