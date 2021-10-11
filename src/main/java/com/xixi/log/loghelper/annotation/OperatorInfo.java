package com.xixi.log.loghelper.annotation;

/**
 * @author shengchengchao
 * @Description
 * @createTime 2021/9/28
 */
public abstract class OperatorInfo<T> {

    abstract T getOperatorInfo(String userId);
}
