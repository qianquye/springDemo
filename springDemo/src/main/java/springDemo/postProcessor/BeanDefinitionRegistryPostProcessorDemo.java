package springDemo.postProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class BeanDefinitionRegistryPostProcessorDemo implements
		BeanDefinitionRegistryPostProcessor {

	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("####BeanDefinitionRegistryPostProcessorDemo ----> postProcessBeanFactory()");

	}

	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("####BeanDefinitionRegistryPostProcessorDemo ----> postProcessBeanDefinitionRegistry()");
	}

}
