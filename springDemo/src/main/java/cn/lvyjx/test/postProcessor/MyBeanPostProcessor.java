package cn.lvyjx.test.postProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

	public MyBeanPostProcessor(){
		System.out.println("【BeanPostProcessor】:MyBeanPostProcessor implements BeanPostProcessor construct!");
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("【BeanPostProcessor-postProcessBeforeInitialization】：beanName="+beanName);
		if(bean instanceof Teacher){
			Teacher teacher = (Teacher)bean;
			 System.out.println("【BeanPostProcessor-postProcessBeforeInitialization】bean.toString before："+teacher.toString());
			teacher.setName("李三");
		  System.out.println("【BeanPostProcessor-postProcessBeforeInitialization】bean.toString after："+teacher.toString());
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if(bean instanceof Teacher){
			Teacher teacher = (Teacher)bean;
			 System.out.println("【BeanPostProcessor-postProcessAfterInitialization】bean.toString ："+teacher.toString());
		}
		System.out.println("【BeanPostProcessor-postProcessAfterInitialization】：beanName="+beanName);
		return bean;
	}

	
}
