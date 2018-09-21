package springDemo.postProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.PriorityOrdered;

public class BeanDefinitionRegistryPostProcessorPriorityOrderedDemo implements
		BeanDefinitionRegistryPostProcessor, PriorityOrdered {

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("BeanDefinitionRegistryPostProcessorPriorityOrderedDemo 实现了BeanDefinitionRegistryPostProcessorPriorityOrderedDemo与PriorityOrdered 方法名 postProcessBeanFactory");

	}
	@Override
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("BeanDefinitionRegistryPostProcessorPriorityOrderedDemo 实现了BeanDefinitionRegistryPostProcessorPriorityOrderedDemo与PriorityOrdered 方法名 postProcessBeanDefinitionRegistry");

	}
	@Override
	public int getOrder() {
		return 0;
	}
}
