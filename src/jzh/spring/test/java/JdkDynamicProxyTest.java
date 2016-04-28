import org.junit.Test;

import aop.AdvisedSupport;
import aop.JdkDynamicProxy;
import aop.TargetSource;
import aop.TimerInterceptor;
import context.ApplicationContext;
import context.ClassPathXmlApplicationContext;


public class JdkDynamicProxyTest {
	@Test
	public void testInterceptor() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
		HelloWorldService helloWorldService = (HelloWorldService)applicationContext.getBean("helloWorldService");
		helloWorldService.helloWorld();
		
		OutputService outputService = (OutputService)applicationContext.getBean("outputService");
		System.out.println();
		
		/*System.out.println("-----------------------------------------------------");
		
		// --------- helloWorldService with AOP
		AdvisedSupport advisedSupport = new AdvisedSupport();
		TargetSource targetSource = new TargetSource(helloWorldService);
		advisedSupport.setTargetSource(targetSource);

		TimerInterceptor timerInterceptor = new TimerInterceptor();
		advisedSupport.setMethodInterceptor(timerInterceptor);

		JdkDynamicProxy jdkDynamicAopProxy = new JdkDynamicProxy(advisedSupport);
		HelloWorldService helloWorldServiceProxy = (HelloWorldService) jdkDynamicAopProxy.getProxy();

        helloWorldServiceProxy.helloWorld();*/
	}
}
