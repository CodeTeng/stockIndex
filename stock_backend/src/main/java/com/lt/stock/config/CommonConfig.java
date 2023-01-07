package com.lt.stock.config;

import com.lt.stock.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @description: 公共配置类
 * @author: ~Teng~
 * @date: 2023/1/6 18:48
 */
@Configuration
public class CommonConfig {

    /**
     * 密码加密器---BCryptPasswordEncoder方法采用SHA-256对密码进行加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 基于雪花算法生成的全局唯一id
     */
    @Bean
    public IdWorker idWorker() {
        // 指定当前为 1 号机房的 2 号机器生成
        return new IdWorker(2L, 1L);
    }
}
