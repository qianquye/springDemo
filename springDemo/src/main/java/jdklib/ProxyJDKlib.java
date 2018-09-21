package jdklib;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyJDKlib implements InvocationHandler {

	private Object target;
	public ProxyJDKlib(Object target){
		this.target = target;
	}
	
	public Object getProxy(){
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
	}
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("处理数据之前");
		method.invoke(target, args);
		return null;
	}

}
