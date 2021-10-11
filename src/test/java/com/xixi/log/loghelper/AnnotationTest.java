package com.xixi.log.loghelper;

import com.xixi.log.loghelper.annotation.DistributeExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shengchengchao
 * @Description
 * @createTime 2021/9/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AnnotationTest {




    @Test
    public void testSpel(){
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("'新增管理员:'+ #username + '，老地址为' + #address");
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("username", "测试老地址");
        context.setVariable("address", "测试新地址");
        String s = expression.getValue(context).toString();
        System.out.println(s);

    }
}
