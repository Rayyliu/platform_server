package com.platform.util;

import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionUtil {

    @SneakyThrows
    public static String assertUtil(int testId, String parameter, String except, String rule, int key){

        key++;
        testId++;
        try {
            switch (rule){
                //查看两个对象是否相等。类似于字符串比较使用的equals()方法
                case "Equals":
                    assertEquals(parameter,except);
                    break;
                //查看对象是否为空
                case "Null":
                    assertNull("需要从result里获取某个字段的返回值，为空则该断言通过","该字段为空判断通过");
                    break;
                //查看对象是否不为空。
                case "NotNull":
                    assertNotNull("需要从result里获取某个字段的返回值，不为空则该断言通过","该字段不为空判断通过");
                    break;
                //查看两个对象的引用是否相等。类似于使用“==”比较两个对象
                case "Same":
                    assertSame(new Object(),new Object());
                    break;
                //查看两个对象的引用是否不相等。类似于使用“!=”比较两个对象
                case "NotSame":
                    assertNotSame(new Object(),new Object());
                    break;
                //查看运行结果是否为true。
                case "True":
                    assertTrue(parameter.equals(except),"相等判断能成功"+parameter+"=="+except);
                    break;
                //查看运行结果是否为false。
                case "False":
                    assertFalse(!parameter.equals(except),"不相等判断能成功:"+parameter+"!="+except);
                    break;
                //让测试失败
                case "Fail":
                    fail();
                    break;
                //查看两个数组是否相等
                case "ArrayEquals":
                    break;
                //查看实际值是否满足指定的条件
                case "assertThat":
                    break;
            }
        }catch (org.opentest4j.AssertionFailedError ae){
//            ae.printStackTrace();
            return "第"+testId+"条用例的断言结果："+"第"+key+"条断言结果："+ae.getMessage();
        }
        return "第"+testId+"条用例的断言结果："+"第"+key+"条断言结果："+"断言成功";
    }
}
