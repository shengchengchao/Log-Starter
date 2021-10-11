package com.xixi.log.loghelper.context;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.*;
import org.springframework.expression.spel.support.*;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shengchengchao
 * @Description
 * @createTime 2021/9/29
 */
public class StandardEvaluationContext implements EvaluationContext {
    private TypedValue rootObject;
    private List<ConstructorResolver> constructorResolvers;
    private List<MethodResolver> methodResolvers;
    private BeanResolver beanResolver;
    private ReflectiveMethodResolver reflectiveMethodResolver;
    private List<PropertyAccessor> propertyAccessors;
    private TypeLocator typeLocator;
    private TypeConverter typeConverter;
    private TypeComparator typeComparator = new StandardTypeComparator();
    private OperatorOverloader operatorOverloader = new StandardOperatorOverloader();
    private final Map<String, Object> variables = new HashMap();

    public StandardEvaluationContext() {
        this.setRootObject((Object)null);
    }

    public StandardEvaluationContext(Object rootObject) {
        this.setRootObject(rootObject);
    }

    public void setRootObject(Object rootObject, TypeDescriptor typeDescriptor) {
        this.rootObject = new TypedValue(rootObject, typeDescriptor);
    }

    public void setRootObject(Object rootObject) {
        this.rootObject = rootObject != null ? new TypedValue(rootObject) : TypedValue.NULL;
    }

    public TypedValue getRootObject() {
        return this.rootObject;
    }

    public void setPropertyAccessors(List<PropertyAccessor> propertyAccessors) {
        this.propertyAccessors = propertyAccessors;
    }

    public List<PropertyAccessor> getPropertyAccessors() {
        this.ensurePropertyAccessorsInitialized();
        return this.propertyAccessors;
    }

    public void addPropertyAccessor(PropertyAccessor accessor) {
        this.ensurePropertyAccessorsInitialized();
        this.propertyAccessors.add(this.propertyAccessors.size() - 1, accessor);
    }

    public boolean removePropertyAccessor(PropertyAccessor accessor) {
        return this.propertyAccessors.remove(accessor);
    }

    public void setConstructorResolvers(List<ConstructorResolver> constructorResolvers) {
        this.constructorResolvers = constructorResolvers;
    }

    public List<ConstructorResolver> getConstructorResolvers() {
        this.ensureConstructorResolversInitialized();
        return this.constructorResolvers;
    }

    public void addConstructorResolver(ConstructorResolver resolver) {
        this.ensureConstructorResolversInitialized();
        this.constructorResolvers.add(this.constructorResolvers.size() - 1, resolver);
    }

    public boolean removeConstructorResolver(ConstructorResolver resolver) {
        this.ensureConstructorResolversInitialized();
        return this.constructorResolvers.remove(resolver);
    }

    public void setMethodResolvers(List<MethodResolver> methodResolvers) {
        this.methodResolvers = methodResolvers;
    }

    public List<MethodResolver> getMethodResolvers() {
        this.ensureMethodResolversInitialized();
        return this.methodResolvers;
    }

    public void addMethodResolver(MethodResolver resolver) {
        this.ensureMethodResolversInitialized();
        this.methodResolvers.add(this.methodResolvers.size() - 1, resolver);
    }

    public boolean removeMethodResolver(MethodResolver methodResolver) {
        this.ensureMethodResolversInitialized();
        return this.methodResolvers.remove(methodResolver);
    }

    public void setBeanResolver(BeanResolver beanResolver) {
        this.beanResolver = beanResolver;
    }

    public BeanResolver getBeanResolver() {
        return this.beanResolver;
    }

    public void setTypeLocator(TypeLocator typeLocator) {
        Assert.notNull(typeLocator, "TypeLocator must not be null");
        this.typeLocator = typeLocator;
    }

    public TypeLocator getTypeLocator() {
        if (this.typeLocator == null) {
            this.typeLocator = new StandardTypeLocator();
        }

        return this.typeLocator;
    }

    public void setTypeConverter(TypeConverter typeConverter) {
        Assert.notNull(typeConverter, "TypeConverter must not be null");
        this.typeConverter = typeConverter;
    }

    public TypeConverter getTypeConverter() {
        if (this.typeConverter == null) {
            this.typeConverter = new StandardTypeConverter();
        }

        return this.typeConverter;
    }

    public void setTypeComparator(TypeComparator typeComparator) {
        Assert.notNull(typeComparator, "TypeComparator must not be null");
        this.typeComparator = typeComparator;
    }

    public TypeComparator getTypeComparator() {
        return this.typeComparator;
    }

    public void setOperatorOverloader(OperatorOverloader operatorOverloader) {
        Assert.notNull(operatorOverloader, "OperatorOverloader must not be null");
        this.operatorOverloader = operatorOverloader;
    }

    public OperatorOverloader getOperatorOverloader() {
        return this.operatorOverloader;
    }

    public void setVariable(String name, Object value) {
        this.variables.put(name, value);
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables.putAll(variables);
    }

    public void registerFunction(String name, Method method) {
        this.variables.put(name, method);
    }

    public Object lookupVariable(String name) {
        return this.variables.get(name);
    }

    public void registerMethodFilter(Class<?> type, MethodFilter filter) throws IllegalStateException {
        this.ensureMethodResolversInitialized();
        if (this.reflectiveMethodResolver != null) {
            this.reflectiveMethodResolver.registerMethodFilter(type, filter);
        } else {
            throw new IllegalStateException("Method filter cannot be set as the reflective method resolver is not in use");
        }
    }

    private void ensurePropertyAccessorsInitialized() {
        if (this.propertyAccessors == null) {
            this.initializePropertyAccessors();
        }

    }

    private synchronized void initializePropertyAccessors() {
        if (this.propertyAccessors == null) {
            List<PropertyAccessor> defaultAccessors = new ArrayList();
            defaultAccessors.add(new ReflectivePropertyAccessor());
            this.propertyAccessors = defaultAccessors;
        }

    }

    private void ensureConstructorResolversInitialized() {
        if (this.constructorResolvers == null) {
            this.initializeConstructorResolvers();
        }

    }

    private synchronized void initializeConstructorResolvers() {
        if (this.constructorResolvers == null) {
            List<ConstructorResolver> defaultResolvers = new ArrayList();
            defaultResolvers.add(new ReflectiveConstructorResolver());
            this.constructorResolvers = defaultResolvers;
        }

    }

    private void ensureMethodResolversInitialized() {
        if (this.methodResolvers == null) {
            this.initializeMethodResolvers();
        }

    }

    private synchronized void initializeMethodResolvers() {
        if (this.methodResolvers == null) {
            List<MethodResolver> defaultResolvers = new ArrayList();
            this.reflectiveMethodResolver = new ReflectiveMethodResolver();
            defaultResolvers.add(this.reflectiveMethodResolver);
            this.methodResolvers = defaultResolvers;
        }

    }
}
