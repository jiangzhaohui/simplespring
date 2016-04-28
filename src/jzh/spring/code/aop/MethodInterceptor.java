package aop;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

public interface MethodInterceptor {
	public boolean needMatch(Method method);
	
	public Object invoke(Object object, Method method,Object[] args, 
			List<MethodInterceptor> MethodInterceptor, int index, int type)  throws Throwable;
}
