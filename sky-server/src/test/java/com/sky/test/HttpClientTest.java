package com.sky.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HttpClientTest {
//     通过httpclient发送一个get请求
    @Test
    public void testGet()throws Exception{
        //创建httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建请求对象

        HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");


//        发送请求对象,接受请求发送响应结果
        CloseableHttpResponse response = httpclient.execute(httpGet);
//        获取服务端返回的状态码
        int status =response.getStatusLine().getStatusCode();
        System.out.println("店铺营业状态是"+status);
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println("服务端返回的数据为：" + body);
        response.close();
        httpclient.close();
    }

//     通过httpclient发送一个post请求
    @Test
    public void testPost()throws  Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "admin");
        jsonObject.put("password", "123456");
        StringEntity entity=new StringEntity(jsonObject.toString());
        // 指定请求编码方式
        entity.setContentEncoding("utf-8");
// 数据格式
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        CloseableHttpResponse response = httpclient.execute(httpPost);

        int status =response.getStatusLine().getStatusCode();
        System.out.println("店铺营业状态是"+status);
        HttpEntity entity1 = response.getEntity();
        String body = EntityUtils.toString(entity1);
        System.out.println("服务端返回的数据为：" + body);
        response.close();
        httpclient.close();

    }
}
