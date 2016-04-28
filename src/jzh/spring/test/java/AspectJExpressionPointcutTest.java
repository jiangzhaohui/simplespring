import org.junit.Test;

import aop.AspectJExpressionPointcut;

public class AspectJExpressionPointcutTest {

	@Test
    public void testClassFilter() throws Exception {
//        String expression = "execution(* *.*(..))";
		String expression = "execution(* *.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut(expression);
        boolean match = aspectJExpressionPointcut.matches(HelloWorldService.class);
        System.out.println(match);
    }
	
	@Test
    public void testMethodInterceptor() throws Exception {
        String expression = "execution(* *.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut(expression);
        boolean match = aspectJExpressionPointcut.matches(HelloWorldServiceImpl.class.getDeclaredMethod("helloWorld"));
        System.out.println(match);
    }
}
