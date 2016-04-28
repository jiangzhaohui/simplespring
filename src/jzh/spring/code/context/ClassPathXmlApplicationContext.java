package context;

import java.io.IOException;

import factory.AbstractBeanFactory;
import factory.AutowireCapableBeanFactory;
import xml.XmlBeanDefinitionReader;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
	String configLocation;

	public ClassPathXmlApplicationContext(String configLocation) throws Exception{
		this(configLocation, new AutowireCapableBeanFactory());
	}
	
	public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
		super(beanFactory);
		this.configLocation = configLocation;
		refresh();
	}
	
	@Override
	public void refresh() throws Exception{
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(configLocation);
		xmlBeanDefinitionReader.loadBeanDefinitions();
		
		for(String name : xmlBeanDefinitionReader.getRegistry().keySet()){
			beanFactory.getBeanDefinitionMap().put(name, xmlBeanDefinitionReader.getRegistry().get(name));
		}
	}


}
