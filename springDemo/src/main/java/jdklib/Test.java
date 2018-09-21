package jdklib;

import java.lang.reflect.Proxy;

public class Test {

	
	 public static void main(String[] args) {
		 ProxyInterface proxyImpl = new ProxyImpl();
		ProxyJDKlib jdkLib = new ProxyJDKlib(proxyImpl);
		ProxyInterface proxy = (ProxyInterface)jdkLib.getProxy();
		proxy.save();
		
		ProxyImpl  proxyImpl1 = new ProxyImpl();
		proxyImpl1.setData("e", "1");
		ProxyImpl  proxyImpl2 = new ProxyImpl();
		System.out.println(proxyImpl2.getData("e"));
	}
}
