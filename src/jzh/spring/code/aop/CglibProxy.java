package aop;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import util.Constants;


public class CglibProxy implements MethodInterceptor {
	
	private AdvisedSupport advised;

	public CglibProxy(AdvisedSupport advised) {
		this.advised = advised;
	}

	public Object getProxy() {
		Enhancer enhancer = new Enhancer();  
		enhancer.setSuperclass(advised.getTargetSource().getTarget().getClass());  
		enhancer.setCallback(this);
        Object enhanced = enhancer.create();
		return enhanced;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		
		List<aop.MethodInterceptor> methodInterceptors = advised.getMethodInterceptors();
		if(methodInterceptors.size()==0){
			return method.invoke(advised.getTargetSource().getTarget(), args);
		}
		aop.MethodInterceptor start = methodInterceptors.get(Constants.METHODINTERCEPTOR_START);
		return start.invoke(
				advised.getTargetSource().getTarget(),method, args,
				methodInterceptors, Constants.METHODINTERCEPTOR_START,
				Constants.DYNAMIC_PROXY_CGLIB);
	}
	


}
