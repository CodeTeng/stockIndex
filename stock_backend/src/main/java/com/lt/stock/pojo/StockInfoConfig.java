package com.lt.stock.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/7 12:51
 */
@ConfigurationProperties(prefix = "stock")
@Data
public class StockInfoConfig {
    /**
     * A股大盘ID集合
     */
    private List<String> inner;

    /**
     * 外盘ID集合
     */
    private List<String> outer;

    /**
     * 股票区间
     */
    private List<String> upDownRange;

    /**
     * 大盘参数获取url
     */
    private String marketUrl;

    /**
     * 板块参数获取url
     */
    private String blockUrl;
}
