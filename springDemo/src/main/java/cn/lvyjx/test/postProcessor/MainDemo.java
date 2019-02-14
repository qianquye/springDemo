package cn.lvyjx.test.postProcessor;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainDemo {

	public static void main(String[] args) {
		System.out.println("开始执行");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-postprocessor.xml");
		Teacher teacher = (Teacher)context.getBean("teacher");
		System.out.println(teacher.toString());
		System.out.println("结束执行");
	}
}
