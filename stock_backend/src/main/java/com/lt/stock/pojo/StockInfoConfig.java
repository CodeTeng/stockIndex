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
}
