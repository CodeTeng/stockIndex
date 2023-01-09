package com.lt.stock.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/9 23:00
 */
@ConfigurationProperties(prefix = "task.pool")
@Data
public class TaskThreadPoolConfig {
    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer keepAliveSeconds;
    private Integer queueCapacity;
}
