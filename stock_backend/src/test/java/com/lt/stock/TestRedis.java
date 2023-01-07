package com.lt.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/7 10:15
 */
@SpringBootTest
public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test1() {
        stringRedisTemplate.opsForValue().set("myname", "zhangsan");
        String myname = stringRedisTemplate.opsForValue().get("myname");
        System.out.println(myname);
    }
}
