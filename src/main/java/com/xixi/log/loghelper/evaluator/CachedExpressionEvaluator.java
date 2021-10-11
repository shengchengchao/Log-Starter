package com.xixi.log.loghelper.evaluator;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @author shengchengchao
 * @Description
 * @createTime 2021/9/29
 */
public class CachedExpressionEvaluator {

    private final SpelExpressionParser parser;
    private final ParameterNameDiscoverer parameterNameDiscoverer;

    protected CachedExpressionEvaluator(SpelExpressionParser parser) {
        this.parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        Assert.notNull(parser, "SpelExpressionParser must not be null");
        this.parser = parser;
    }

    protected CachedExpressionEvaluator() {
        this(new SpelExpressionParser());
    }

    protected SpelExpressionParser getParser() {
        return this.parser;
    }

    protected ParameterNameDiscoverer getParameterNameDiscoverer() {
        return this.parameterNameDiscoverer;
    }

    protected Expression getExpression(Map<ExpressionKey, Expression> cache, AnnotatedElementKey elementKey, String expression) {
        CachedExpressionEvaluator.ExpressionKey expressionKey = this.createKey(elementKey, expression);
        Expression expr = (Expression)cache.get(expressionKey);
        if (expr == null) {
            expr = this.getParser().parseExpression(expression);
            cache.put(expressionKey, expr);
        }

        return expr;
    }

    private CachedExpressionEvaluator.ExpressionKey createKey(AnnotatedElementKey elementKey, String expression) {
        return new CachedExpressionEvaluator.ExpressionKey(elementKey, expression);
    }

    protected static class ExpressionKey implements Comparable<CachedExpressionEvaluator.ExpressionKey> {
        private final AnnotatedElementKey element;
        private final String expression;

        protected ExpressionKey(AnnotatedElementKey element, String expression) {
            this.element = element;
            this.expression = expression;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            } else if (!(other instanceof CachedExpressionEvaluator.ExpressionKey)) {
                return false;
            } else {
                CachedExpressionEvaluator.ExpressionKey otherKey = (CachedExpressionEvaluator.ExpressionKey)other;
                return this.element.equals(otherKey.element) && ObjectUtils.nullSafeEquals(this.expression, otherKey.expression);
            }
        }

        public int hashCode() {
            return this.element.hashCode() + (this.expression != null ? this.expression.hashCode() * 29 : 0);
        }

        public String toString() {
            return this.element + (this.expression != null ? " with expression \"" + this.expression : "\"");
        }

        public int compareTo(CachedExpressionEvaluator.ExpressionKey other) {
            int result = this.element.toString().compareTo(other.element.toString());
            if (result == 0 && this.expression != null) {
                result = this.expression.compareTo(other.expression);
            }

            return result;
        }
    }

}
