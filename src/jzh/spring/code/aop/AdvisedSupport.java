package aop;

import java.util.List;

public class AdvisedSupport {
	TargetSource targetSource;
	List<MethodInterceptor> methodInterceptors;
	
	public TargetSource getTargetSource() {
		return targetSource;
	}

	public void setTargetSource(TargetSource targetSource) {
		this.targetSource = targetSource;
	}

	public List<MethodInterceptor> getMethodInterceptors() {
		return methodInterceptors;
	}

	public void setMethodInterceptors(List<MethodInterceptor> methodInterceptors) {
		this.methodInterceptors = methodInterceptors;
	}

	
}
