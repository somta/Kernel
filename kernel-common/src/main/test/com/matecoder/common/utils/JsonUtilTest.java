package com.matecoder.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.matecoder.core.protocol.ResponseDataResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class JsonUtilTest {

    @Test
    public void serializeTest() throws JsonProcessingException {
        ResponseDataResult<Student> responseDataResult = new ResponseDataResult<>();
        responseDataResult.setSuccess(true);

        Student student = new Student();
        student.setName("明天的地平线");
        student.setAge(18);
        responseDataResult.setResult( student);

        System.out.println(JsonUtil.serialize(responseDataResult));
    }

    @Test
    public void simpleGenericObjectTest() throws JsonProcessingException {
        String genericObjectString = """
                {"success":true,"errorCode":0,"errorMsg":null,"result":"helloWorld"}
                """;
        ResponseDataResult<String> result = JsonUtil.deserialize(genericObjectString, ResponseDataResult.class, String.class);
        Assertions.assertEquals(result.getResult(),"helloWorld");
    }


    @Test
    public void genericObjectTest() throws JsonProcessingException {
        String genericObjectString = """
                {"success":true,"errorCode":0,"errorMsg":null,"result":{"name":"明天的地平线","age":18}}
                """;
        ResponseDataResult<Student> result = JsonUtil.deserialize(genericObjectString, ResponseDataResult.class, Student.class);
        Assertions.assertEquals(result.getResult().getName(),"明天的地平线");
    }

    @Test
    public void genericObjectMapTest() throws JsonProcessingException {
        String genericObjectString = """
                {"user":{"name":"明天的地平线","age":18}}
                """;
        Map<String,Student> result = JsonUtil.deserialize(genericObjectString, Map.class, String.class,Student.class);
        Assertions.assertEquals(result.get("user").getName(),"明天的地平线");
    }


    @Test
    public void genericObjectListTest() throws JsonProcessingException {
        String genericObjectString = """
                {"success":true,"errorCode":0,"errorMsg":null,"result":[{"name":"明天的地平线","age":18}]}
                """;
        JavaType studentListJavaType = JsonUtil.getGenericsType(List.class, Student.class);
        JavaType javaType = JsonUtil.getNestedGenericsType(ResponseDataResult.class,studentListJavaType);
        ResponseDataResult<List<Student>> result = JsonUtil.deserialize(genericObjectString, javaType);
        Assertions.assertEquals(result.getResult().getFirst().getName(),"明天的地平线");
    }

    static class Student {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
