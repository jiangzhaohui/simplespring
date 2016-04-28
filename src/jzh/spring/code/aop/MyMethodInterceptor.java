package aop;

import java.lang.reflect.Method;
import java.util.List;

import util.Constants;

public class MyMethodInterceptor implements MethodInterceptor {
	String expression;
	
	@Override
	public boolean needMatch(Method method){
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut(expression);
        boolean match = aspectJExpressionPointcut.matches(method);
        return match;
	}

	@Override
	public Object invoke(Object object, Method method,Object[] args, 
			List<MethodInterceptor> MethodInterceptor, int index, int type) throws Throwable {
		
		
		Object o = null;
		if(index==MethodInterceptor.size()-1){
			if(type==Constants.DYNAMIC_PROXY_JDK){//jdk dynamic
				System.err.println("called by jdk dynamic");
				o = method.invoke(object, args);
			}else if(type==Constants.DYNAMIC_PROXY_CGLIB){//cglib proxy
				System.err.println("called by cglib proxy");
//				o = proxy.invokeSuper(object, args);
				o = method.invoke(object, args);
			}
		}else{
			o = MethodInterceptor.get(index+1).invoke(object, method, args,
					MethodInterceptor, index+1, type);
		}
		return o;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	
	
}
