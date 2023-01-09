package com.lt.stock.config.job;

import com.lt.stock.service.StockTimerTaskService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 定义股票相关数据的定时任务
 * @author: ~Teng~
 * @date: 2023/1/9 21:45
 */
@Component
public class StockJob {
    private static Logger logger = LoggerFactory.getLogger(StockJob.class);

    @Autowired
    private StockTimerTaskService stockTimerTaskService;

    /**
     * 采集国内大盘数据
     */
    @XxlJob("collectionInnerMarketInfo")
    public void collectionInnerMarketInfo() {
        stockTimerTaskService.collectInnerMarketInfo();
    }

    /**
     * 采集股票数据
     */
    @XxlJob("collectAShareInfo")
    public void collectAShareInfo() {
        stockTimerTaskService.collectAShareInfo();
    }

    /**
     * 采集板块数据
     */
    @XxlJob("collectStockSectorRtIndex")
    public void collectStockSectorRtIndex() {
        stockTimerTaskService.collectStockSectorRtIndex();
    }
}
