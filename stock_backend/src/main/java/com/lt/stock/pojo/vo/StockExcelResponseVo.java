package com.lt.stock.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description: 导出股票信息的实体类对象
 * @author: ~Teng~
 * @date: 2023/1/7 19:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockExcelResponseVo {
    @ExcelProperty(value = {"股票涨幅信息统计表", "股票编码"}, index = 0)
    private String code;

    @ExcelProperty(value = {"股票涨幅信息统计表", "股票名称"}, index = 1)
    private String name;

    @ExcelProperty(value = {"股票涨幅信息统计表", "前收盘价格"}, index = 2)
    private BigDecimal preClosePrice;

    @ExcelProperty(value = {"股票涨幅信息统计表", "当前价格"}, index = 3)
    private BigDecimal tradePrice;

    @ExcelProperty(value = {"股票涨幅信息统计表", "涨跌"}, index = 4)
    private BigDecimal increase;

    @ExcelProperty(value = {"股票涨幅信息统计表", "涨幅"}, index = 5)
    private BigDecimal upDown;

    @ExcelProperty(value = {"股票涨幅信息统计表", "振幅"}, index = 6)
    private BigDecimal amplitude;

    @ExcelProperty(value = {"股票涨幅信息统计表", "交易总量"}, index = 7)
    private BigDecimal tradeAmt;

    @ExcelProperty(value = {"股票涨幅信息统计表", "交易总金额"}, index = 8)
    private BigDecimal tradeVol;

    @ExcelProperty(value = {"股票涨幅信息统计表", "日期"}, index = 9)
    private String curDate;
}
