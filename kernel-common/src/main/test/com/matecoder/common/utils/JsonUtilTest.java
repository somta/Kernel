package com.matecoder.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.matecoder.core.protocol.ResponseDataResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonUtilTest {

    @Test
    public void serializeTest() throws JsonProcessingException {
        ResponseDataResult responseDataResult = new ResponseDataResult();
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
