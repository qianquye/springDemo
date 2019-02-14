package cn.lvyjx.test.postProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 对象实例化前执行
 * @author Administrator
 *
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor{

	public MyBeanFactoryPostProcessor(){
		System.out.println("【BeanFactoryPostProcessor】:MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor construct!");
	}
	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("【BeanFactoryPostProcessor】:postProcessBeanFactory method!");
		//对 teacher对象赋值/修改
       BeanDefinition bd = beanFactory.getBeanDefinition("teacher");
       bd.getPropertyValues().addPropertyValue("name", "李二");
       bd.getPropertyValues().addPropertyValue("phone", "13256487442");
      // System.out.println("before："+bd.getAttribute("lazy-init"));
      // bd.setAttribute("lazy-init", "true");
      // System.out.println("after:"+bd.getAttribute("lazy-init"));
	}

}
