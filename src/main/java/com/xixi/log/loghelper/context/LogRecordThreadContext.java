package com.xixi.log.loghelper.context;


import java.util.Map;
import java.util.Stack;

/**
 * @author shengchengchao
 * @Description
 * @createTime 2021/9/29
 */
public class LogRecordThreadContext {


    InheritableThreadLocal<Stack<Map<String, Object>>> threadLocal = new InheritableThreadLocal<>();



    public  Map<String, Object> getVariables(){
        return threadLocal.get().pop();
    }

    public void setVariables(Map<String, Object> item){
        threadLocal.get().push(item);
    }
}
