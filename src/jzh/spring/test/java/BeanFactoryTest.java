
import org.junit.Test;

import factory.AbstractBeanFactory;
import factory.AutowireCapableBeanFactory;
import xml.XmlBeanDefinitionReader;


public class BeanFactoryTest {
	
	@Test
	public void test() throws Exception {
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader("tinyioc.xml");
		xmlBeanDefinitionReader.loadBeanDefinitions();
		
		AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();
		
		for(String name : xmlBeanDefinitionReader.getRegistry().keySet()){
			beanFactory.getBeanDefinitionMap().put(name, xmlBeanDefinitionReader.getRegistry().get(name));
		}
		beanFactory.preInstantiateSingletons();
		
		HelloWorldService helloWorldService = (HelloWorldService)beanFactory.getBean("helloWorldService");
		helloWorldService.helloWorld();
	}
}
