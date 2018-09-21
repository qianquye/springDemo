package springDemo.postProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;

public class BeanDefinitionRegistryPostProcessorOrderDemo implements
		BeanDefinitionRegistryPostProcessor, Ordered {

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("BeanDefinitionRegistryPostProcessorOrderDemo 实现了BeanDefinitionRegistryPostProcessorPriorityOrderedDemo与Ordered 方法名 postProcessBeanFactory");

	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("BeanDefinitionRegistryPostProcessorOrderDemo 实现了BeanDefinitionRegistryPostProcessorPriorityOrderedDemo与Ordered 方法名 postProcessBeanDefinitionRegistry");

	}

}
