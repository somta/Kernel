# somta-xss-starter-example

#### 介绍
xss的测试工程，测试各种情况下xss能力是否生效

#### 测试说明
##### 1.测试接口/xss/testJsonXss,当请求体为json格式时，出现XSS攻击是否能处理

接口地址：http://localhost:8080/xss/testJsonXss

入参

```
{
    "id":11,
    "title":"这是一个测试文章",
    "content":"<table onclick='alert('gangan')'>test</table>"
}
```

结果被转义成功

```
{
    "id": 11,
    "title": "这是一个测试文章",
    "content": "&lt;table onclick=&#39;alert(&#39;gangan&#39;)&#39;&gt;test&lt;/table&gt;"
}
```



##### 2.测试接口/xss/testFormXss,当请求体为form格式时，出现XSS攻击是否能处理

接口地址：http://localhost:8080/xss/testJsonXss

入参

使用x-www-form-urlencoded配置上面的参数

结果被转义成功

```
{
    "id": 11,
    "title": "这是一个测试文章",
    "content": "&lt;table onclick=&#39;alert(&#39;gangan&#39;)&#39;&gt;test&lt;/table&gt;"
}
```



##### 3.测试接口/xss/testIgnoreXssClean,有些特殊接口，如富文本文章内容提交时，不能处理，需要忽略，测试@IgnoreXssClean是否生效

测试接口：http://localhost:8080/xss/testIgnoreXssClean

入参

```
{
    "id":11,
    "title":"这是一个测试文章",
    "content":"<table onclick='alert('gangan')'>test</table>"
}
```

结果由于加了@IgnoreXssClean注解，没有被处理

```
{
    "id": 11,
    "title": "这是一个测试文章",
    "content": "<table onclick='alert('gangan')'>test</table>"
}
```



