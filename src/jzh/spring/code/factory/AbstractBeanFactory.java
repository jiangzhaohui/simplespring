package factory;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sun.misc.ProxyGenerator;
import aop.AdvisedSupport;
import aop.CglibProxy;
import aop.MethodInterceptor;
import aop.TargetSource;
import base.BeanDefinition;

public abstract class AbstractBeanFactory implements BeanFactory {
	
	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

	public Map<String, BeanDefinition> getBeanDefinitionMap() {
		return beanDefinitionMap;
	}

	@Override
	public Object getBean(String name) throws Exception {
		BeanDefinition beanDefinition = beanDefinitionMap.get(name);
		if(beanDefinition==null){
			throw new Exception("bean not exist");
		}
		Object bean = beanDefinition.getBean();
		if(bean==null){
			bean = doCreateBean(beanDefinition);
		}
//		return bean;
		return genProxyBean(bean);
	}
	
	public Object genProxyBean(Object bean) throws Exception{
		if(bean instanceof MethodInterceptor)
			return bean;
		if(bean instanceof TargetSource)
			return bean;
		if(bean instanceof AdvisedSupport)
			return bean;
		AdvisedSupport advisedSupport = new AdvisedSupport();
		TargetSource targetSource = new TargetSource(bean);
		advisedSupport.setTargetSource(targetSource);
		
		List<MethodInterceptor>  methodInterceptors = getBeansForType(MethodInterceptor.class);
		advisedSupport.setMethodInterceptors(methodInterceptors);

		/*JdkDynamicProxy jdkDynamicAopProxy = new JdkDynamicProxy(advisedSupport);
		Object o = Proxy.newProxyInstance(this.getClass().getClassLoader(), 
				bean.getClass().getInterfaces(), 
				jdkDynamicAopProxy);*/
//		createProxyClassFile(o.getClass().getName(),o.getClass());
		CglibProxy cglibProxy = new CglibProxy(advisedSupport);
		Object o = cglibProxy.getProxy();
		return o;
	}
	
	public void preInstantiateSingletons() throws Exception {
		for(String name : beanDefinitionMap.keySet()){
			getBean(name);
		}
	}
	
	public void createProxyClassFile(String name, Class clazz) {
		byte[] data = ProxyGenerator.generateProxyClass(name, clazz.getInterfaces());
		try {
			FileOutputStream out = new FileOutputStream(name + ".class");
			out.write(data);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public <T>  List<T> getBeansForType(Class<T> clazz) throws Exception{
		List<T> methodInterceptors = new ArrayList<T>();
		for(String name : beanDefinitionMap.keySet()){
			String clazzName = beanDefinitionMap.get(name).getBeanClassName();
			if(clazz.isAssignableFrom(Class.forName(clazzName))){
				Object bean = getBean(name);
				methodInterceptors.add((T)bean);
			}
		}
		return methodInterceptors;
	}

	protected abstract Object doCreateBean(BeanDefinition beanDefinition) throws Exception;

}
