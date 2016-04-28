package aop;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.aspectj.weaver.tools.ShadowMatch;

public class AspectJExpressionPointcut {

	private PointcutParser pointcutParser;

	private String expression;

	private PointcutExpression pointcutExpression;
	
	private static final Set<PointcutPrimitive> DEFAULT_SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();

	static {
		DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
	}
	
	public AspectJExpressionPointcut(String expression){
		this(DEFAULT_SUPPORTED_PRIMITIVES);
		this.expression = expression;
		pointcutExpression = pointcutParser.parsePointcutExpression(expression);
	}
	
	public AspectJExpressionPointcut(Set<PointcutPrimitive> supportedPrimitives) {
		pointcutParser = PointcutParser
				.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(supportedPrimitives);
	}
	
	public boolean matches(Class clazz){
		return pointcutExpression.couldMatchJoinPointsInType(clazz);
	}
	
	public boolean matches(Method method){
		ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);
		return shadowMatch.alwaysMatches();
	}
}
