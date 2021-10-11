package com.xixi.log.loghelper.annotation;

/**
 * @author shengchengchao
 * @Description
 * @createTime 2021/9/27
 */
public @interface LogRecordAnnotation {
    /**
     * 成功模版
     * @return
     */
    String success();

    /**
     * 失败模版
     * @return
     */
    String fail() default "";

    /**
     * 操作人
     * @return
     */
    String operator() default "";

    /**
     * 业务id
     * @return
     */
    String bizNo();

    /**
     * 操作日志类型
     * @return
     */
    String category() default "";

    /**
     * 详情
     * @return
     */
    String detail() default "";

    /**
     * 记录日志条件
     * @return
     */
    String condition() default "";

    /**
     * 获得用户信息的类
     * @return
     */
    Class<? extends OperatorInfo> operateClass();
}


