package com.lt.stock.service;

/**
 * @description: 定义采集股票数据的定时任务的服务接口
 * @author: ~Teng~
 * @date: 2023/1/9 16:42
 */
public interface StockTimerTaskService {

    /**
     * 定义采集国内大盘的实时数据信息
     */
    void collectInnerMarketInfo();

    /**
     * 采集股票数据
     */
    void collectAShareInfo();

    /**
     * 采集板块数据
     */
    void collectStockSectorRtIndex();
}
