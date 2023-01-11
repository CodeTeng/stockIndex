package com.lt.stock.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: http连接池常规参数的配置实体类
 * @author: ~Teng~
 * @date: 2023/1/11 18:14
 */
@Data
@ConfigurationProperties(prefix = "http.pool")
public class HttpPoolConfig {
    /**
     * 最大连接数
     */
    private Integer maxConnectionTotal;

    /**
     * 指定服务每次能并行接收的请求数量
     */
    private Integer routeMaxCount;

    /**
     * 空闲连接超时时间超时后小于连接会被回收
     */
    private Integer connectionIdleTimeOut;

    /**
     * 请求失败重试次数
     */
    private Integer retryCount;

    /**
     * 连接超时时间
     */
    private Integer connectionTimeOut;

    /**
     * 读取数据超时时间
     */
    private Integer readTimeOut;

    /**
     * 连接池不够用时，等待超时时间
     */
    private Integer connectionRequestTimeOut;
}
