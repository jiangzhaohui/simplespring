package aop;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import sun.misc.ProxyGenerator;
import util.Constants;


public class JdkDynamicProxy implements InvocationHandler {
	
	private AdvisedSupport advised;

	public JdkDynamicProxy(AdvisedSupport advised) {
		this.advised = advised;
	}

	public Object getProxy() {
		Object o = Proxy.newProxyInstance(this.getClass().getClassLoader(), 
				advised.getTargetSource().getTarget().getClass().getInterfaces(), 
				this);
		System.err.println(o.getClass().getName());
//		createProxyClassFile(o.getClass().getName());
		return o;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		List<MethodInterceptor> methodInterceptors = advised.getMethodInterceptors();
		MethodInterceptor start = methodInterceptors.get(Constants.METHODINTERCEPTOR_START);
		if(methodInterceptors.size()==0){
			return method.invoke(advised.getTargetSource().getTarget(), args);
		}
		return start.invoke(
				advised.getTargetSource().getTarget(),method, args,
				methodInterceptors, Constants.METHODINTERCEPTOR_START,
				Constants.DYNAMIC_PROXY_JDK);
	}
	
	public void createProxyClassFile(String name) {
		byte[] data = ProxyGenerator.generateProxyClass(name, advised.getTargetSource().getTarget().getClass().getInterfaces());
		try {
			FileOutputStream out = new FileOutputStream(name + ".class");
			out.write(data);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
