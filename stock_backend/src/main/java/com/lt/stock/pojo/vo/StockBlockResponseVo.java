package com.lt.stock.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 股票板块返回数据
 * @author: ~Teng~
 * @date: 2023/1/7 16:58
 */
@Data
public class StockBlockResponseVo {
    /**
     * 表示如：new_blhy-玻璃行业
     */
    private String code;

    /**
     * 板块名称
     */
    private String name;

    /**
     * 公司数量
     */
    private Integer companyNum;

    /**
     * 平均价格
     */
    private BigDecimal avgPrice;

    /**
     * 涨跌幅
     */
    private BigDecimal updownRate;

    /**
     * 交易量
     */
    private Long tradeAmt;

    /**
     * 交易金额
     */
    private BigDecimal tradeVol;

    /**
     * 当前日期（精确到秒）
     */
    private String curDate;
}
