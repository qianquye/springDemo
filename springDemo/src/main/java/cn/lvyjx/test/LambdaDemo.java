package cn.lvyjx.test;

public class LambdaDemo {

	public static void main(String[] args) {
		LambdaInterface lambdaImpl = (int a,int b) ->{
			return a+b;
		};
		System.out.println(lambdaImpl.add(4,5));
		LambdaInterface.update("更新");
		lambdaImpl.get("获取");
	}
}

/**
 * lambda编程接口的接口方法有且只有一个接口方法，java8后可以
 * 有默认的方法，默认方法要有default关键字。默认方法需要实例化才能看到
 * 也可以有静态方法，静态方法可以直接调用
 * @author Administrator
 *
 */
interface LambdaInterface{
	
	public int add(int a,int b);
	
	//public void update(String a);
	static void update(String a){
		System.out.println("update:"+a);
	}
	public default void get(String a){
		System.out.println("get:"+a);
	}
}
interface LambdaInterface2<T>{
	  T getObject();
 }

