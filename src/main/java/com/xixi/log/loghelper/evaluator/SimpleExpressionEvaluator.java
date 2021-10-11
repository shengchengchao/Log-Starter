package com.xixi.log.loghelper.evaluator;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shengchengchao
 * @Description
 * @createTime 2021/9/29
 */
public class SimpleExpressionEvaluator extends CachedExpressionEvaluator {

    private final Map<ExpressionKey, Expression> conditionCache = new ConcurrentHashMap<ExpressionKey, Expression>(64);

    public EvaluationContext createMethodBasedEvaluationContext(Method method, Object[] args, BeanFactory beanFactory) {
        MethodBasedEvaluationContext evaluationContext =
                new MethodBasedEvaluationContext(null, method, args, getParameterNameDiscoverer());
        if (beanFactory != null) {
            evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
        }
        return evaluationContext;
    }

    public <T> T eval(String expression, AnnotatedElementKey elementKey, EvaluationContext evalContext) {
        return (T) getExpression(elementKey, expression).getValue(evalContext);
    }

    public Expression getExpression(AnnotatedElementKey elementKey, String expression) {
        return getExpression(this.conditionCache, elementKey, expression);
    }

}
