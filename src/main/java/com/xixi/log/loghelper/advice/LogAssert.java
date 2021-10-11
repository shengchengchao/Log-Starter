package com.xixi.log.loghelper.advice;

import com.xixi.log.loghelper.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author shengchengchao
 * @Description
 * @createTime 2021/8/3
 */
@Slf4j
public class LogAssert {


    public static void meetCondition(boolean condition, Integer code, String msg) {
        if (condition) {
            throw new LogException(code, msg);
        }
    }

    public static void meetCondition(boolean condition, String msg) {
        if (condition) {
            throw new LogException(ResultCodeEnum.BUSINESS_FAILED.getCode(), msg);
        }
    }

    public static void meetCondition(boolean condition, String msg,String var1, Throwable var2) {
        if (condition) {
            log.error(var1,var2);
            throw new LogException(ResultCodeEnum.BUSINESS_FAILED.getCode(), msg);
        }
    }

    public static void meetCondition(boolean condition, Integer code, String msg, Map<String, Object> otherInfo) {
        if (condition) {
            throw new LogException(code, msg, otherInfo);
        }
    }
}
