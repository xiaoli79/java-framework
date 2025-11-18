package org.xiaoli.xiaolicommoncore.utils;

import org.junit.jupiter.api.Test;
import org.xiaoli.xiaolicommoncore.utils.domain.TestClass;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {
//  序列化
    @Test
    void obj2String() {
        TestClass testClass = new TestClass();
        testClass.setName("test");
        testClass.setAge(123);
        testClass.setId(123);
        System.out.println(JsonUtil.obj2String(testClass));
    }
//  序列化 格式化~~
    @Test
    void obj2StringPretty() {
        TestClass testClass = new TestClass();
        testClass.setName("test");
        testClass.setAge(123);
        testClass.setId(123);
        System.out.println(JsonUtil.obj2StringPretty(testClass));


    }

//  反序列化
    @Test
    void string2Obj() {
        String str = "{\"id\":123,\"name\":\"test\",\"age\":123}";
        TestClass testClass = JsonUtil.string2Obj(str, TestClass.class);
        System.out.println(testClass);


    }
}