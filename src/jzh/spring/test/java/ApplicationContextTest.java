

import org.junit.Test;

import aop.MyMethodInterceptor;

import context.ApplicationContext;
import context.ClassPathXmlApplicationContext;

public class ApplicationContextTest {

    @Test
    public void test() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }
}
