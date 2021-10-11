package com.xixi.log.loghelper.aop;

import com.xixi.log.loghelper.annotation.DistributeExceptionHandler;
import com.xixi.log.loghelper.evaluator.LogExpressionEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author shengchengchao
 * @Description
 * @createTime 2021/9/29
 */
@Component
@Aspect
@Slf4j
public class DistributeExceptionAspect {


    private LogExpressionEvaluator evaluator = new LogExpressionEvaluator();

    @Pointcut("@annotation(com.xixi.log.loghelper.annotation.DistributeExceptionHandler)")
    private void exceptionHandleMethod() {

    }

    @AfterThrowing(value = "exceptionHandleMethod()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("捕获异常");
        String attachmentId = getAttachmentId(joinPoint);
        // 处理异常情况下的业务
        System.out.println(attachmentId);
    }

    private DistributeExceptionHandler getDistributeExceptionHandler(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getAnnotation(DistributeExceptionHandler.class);
    }

    private String getAttachmentId(JoinPoint joinPoint) {
        DistributeExceptionHandler handler = getDistributeExceptionHandler(joinPoint);
        if (joinPoint.getArgs() == null) {
            return null;
        }
        EvaluationContext evaluationContext = evaluator.createEvaluationContext(joinPoint.getTarget(), joinPoint.getTarget().getClass(), ((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs());
        AnnotatedElementKey methodKey = new AnnotatedElementKey(((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getTarget().getClass());
        return evaluator.condition(handler.attachmentId(), methodKey, evaluationContext);
    }
}

