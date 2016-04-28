package aop;
import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;


public class LogInterceptor extends MyMethodInterceptor{

	@Override
	public Object invoke(Object object, Method method,Object[] args, 
			List<MethodInterceptor> MethodInterceptor, int index, int type)
			throws Throwable {
		if(needMatch(method)){
			System.out.println("log start ... :method="+method.getName()+"|class="+object.getClass().getSimpleName());
		}
		
		Object o = super.invoke(object, method, args, MethodInterceptor, index,type);
		if(needMatch(method)){
			System.out.println("log end ... :method="+method.getName()+"|class="+object.getClass().getSimpleName());
		}
		
		return o;
	}
	
	@Override
	public void setExpression(String expression){
		super.setExpression(expression);
	}

}
