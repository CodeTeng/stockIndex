package com.lt.stock.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description: 外盘指数实体
 * @author: ~Teng~
 * @date: 2023/1/11 18:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OuterMarketResponseVo {
    /**
     * 大盘名称
     */
    private String name;

    /**
     * 当前日期
     */
    private String curTime;

    /**
     * 涨幅
     */
    private BigDecimal updownRate;

    /**
     * 当前价格
     */
    private BigDecimal tradePrice;

    /**
     * 当前大盘点
     */
    private BigDecimal curPoint;
}
