package factory;

import base.BeanDefinition;

public interface BeanFactory {
	Object getBean(String name) throws Exception;
	
}
