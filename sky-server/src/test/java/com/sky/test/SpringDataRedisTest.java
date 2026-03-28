package com.sky.test;

import ch.qos.logback.classic.pattern.SyslogStartConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.Set;
import java.util.concurrent.TimeUnit;

///@SpringBootTest // ⭐️ 关键：加载 Spring 上下文
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplateNotNull() {
        if (redisTemplate == null) {
            System.out.println("❌ redisTemplate 是 null —— 配置类未被扫描！");
        } else {
            System.out.println(redisTemplate);
            System.out.println("✅ redisTemplate 成功注入：" + redisTemplate.getClass());
            // 可以尝试简单操作
            ValueOperations valueOperations = redisTemplate.opsForValue();
            HashOperations hashOperations = redisTemplate.opsForHash();
            ListOperations listOperations = redisTemplate.opsForList();
            SetOperations setOperations = redisTemplate.opsForSet();
            ZSetOperations zSetOperations = redisTemplate.opsForZSet();
            redisTemplate.opsForValue().set("test", "hello");
            Object value = redisTemplate.opsForValue().get("test");
            System.out.println("读取到的值：" + value);
        }
    }
//    操作字符串类型的数据
    @Test
    public void testString(){
        redisTemplate.opsForValue().set("city","北京");
        String city=(String)redisTemplate.opsForValue().get("city");
        System.out.println(city);
        redisTemplate.opsForValue().set("code",1234,3, TimeUnit.MINUTES);
        redisTemplate.opsForValue().setIfAbsent("log","1");
        redisTemplate.opsForValue().setIfAbsent("log","2");
    }
//    操作哈希类型的数据
    @Test
    public void testHash(){
        HashOperations hashOperations= redisTemplate.opsForHash();
        hashOperations.put("100","name","Tom");
        hashOperations.put("100","age","20");
        String name= (String) hashOperations.get("100","name");
        System.out.println(name);
      Set keys=  hashOperations.keys("100");
        System.out.println(keys);
        hashOperations.delete("100","age");


    }


}