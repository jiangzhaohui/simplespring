package factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import aop.AdvisedSupport;
import aop.JdkDynamicProxy;
import aop.TargetSource;
import aop.TimerInterceptor;
import base.BeanDefinition;
import base.BeanReference;
import base.PropertyValue;

public class AutowireCapableBeanFactory extends AbstractBeanFactory {

	@Override
	protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
		Object o = createBeanInstance(beanDefinition);
		beanDefinition.setBean(o);
		applyPropertyValues(o, beanDefinition);
		return o;
	}
	
	protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
		Object bean = beanDefinition.getBeanClass().newInstance();
		return bean;
//		System.err.println(o.getClass().getName());
//		return beanDefinition.getBeanClass().newInstance();
	}
	
	protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
		if(beanDefinition.getPropertyValues()==null)return;
		for(PropertyValue pv : beanDefinition.getPropertyValues()){
			Object value = pv.getValue();
			if (value instanceof BeanReference) {
				BeanReference beanReference = (BeanReference) value;
				value = getBean(beanReference.getName());
			}

			try {
				String methodName = "set" + pv.getName().substring(0, 1).toUpperCase()
						+ pv.getName().substring(1);
				Method declaredMethod = bean.getClass().getDeclaredMethod(methodName, value.getClass());
				declaredMethod.setAccessible(true);

				declaredMethod.invoke(bean, value);
			} catch (NoSuchMethodException e) {
//				e.printStackTrace();
				Field declaredField = bean.getClass().getDeclaredField(pv.getName());
				declaredField.setAccessible(true);
				declaredField.set(bean, value);
			}
		}
	}

}
