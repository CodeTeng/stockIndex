package com.lt.stock.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 股票涨跌信息
 * @author: ~Teng~
 * @date: 2023/1/7 18:11
 */
@Data
public class StockUpDownResponseVo {
    /**
     * 交易量
     */
    private Long tradeAmt;
    /**
     * 前收盘价
     */
    private BigDecimal preClosePrice;
    /**
     * 振幅
     */
    private BigDecimal amplitude;
    /**
     * 股票编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 日期
     */
    private String curDate;
    /**
     * 交易金额
     */
    private BigDecimal tradeVol;
    /**
     * 张涨跌
     */
    private BigDecimal increase;

    /**
     * 涨幅
     */
    private BigDecimal upDown;
    /**
     * 当前价格
     */
    private BigDecimal tradePrice;
}
