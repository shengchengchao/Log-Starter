package com.xixi.log.loghelper.context;

import org.springframework.expression.*;

import java.util.List;

/**
 * @author shengchengchao
 * @Description
 * @createTime 2021/9/29
 */
public interface EvaluationContext {

    TypedValue getRootObject();

    List<ConstructorResolver> getConstructorResolvers();

    List<MethodResolver> getMethodResolvers();

    List<PropertyAccessor> getPropertyAccessors();

    TypeLocator getTypeLocator();

    TypeConverter getTypeConverter();

    TypeComparator getTypeComparator();

    OperatorOverloader getOperatorOverloader();

    BeanResolver getBeanResolver();

    void setVariable(String var1, Object var2);

    Object lookupVariable(String var1);
}
