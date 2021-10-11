package com.xixi.log.loghelper.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author shengchengchao
 * @Description
 * @createTime 2021/9/29
 */
@Slf4j
@Aspect
@Component
public class LogAdvice {



    @Around(value = "@annotation(com.xixi.log.loghelper.annotation.LogRecordAnnotation)")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info(" LogAdvice.invoke 操作日志前置处理 ");
        //  解析前处理数据 需要放到threadLocal的数据 将数据放进去，在解析spel表达式的时候 将数据再次丢进去。
        


       return null;
    }
}
