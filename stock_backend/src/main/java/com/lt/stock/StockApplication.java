package com.lt.stock;

import com.lt.stock.pojo.StockInfoConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/6 18:08
 */
@SpringBootApplication
@MapperScan("com.lt.stock.mapper")
@EnableConfigurationProperties({StockInfoConfig.class})
public class StockApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }
}
