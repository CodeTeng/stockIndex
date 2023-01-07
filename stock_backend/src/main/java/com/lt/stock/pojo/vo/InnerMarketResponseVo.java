package com.lt.stock.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 国内大盘指数数据的Vo
 * @author: ~Teng~
 * @date: 2023/1/7 12:48
 */
@Data
public class InnerMarketResponseVo {
    /**
     * 交易量
     */
    private Long tradeAmt;

    /**
     * 前收盘价格
     */
    private BigDecimal preClosePrice;

    /**
     * 大盘编码
     */
    private String code;

    /**
     * 大盘名称
     */
    private String name;

    /**
     * 当前日期
     */
    private String curDate;

    /**
     * 开盘价
     */
    private BigDecimal openPrice;

    /**
     * 交易金额
     */
    private Long tradeVol;

    /**
     * 涨幅
     */
    private BigDecimal upDown;

    /**
     * 当前价格
     */
    private BigDecimal tradePrice;
}
