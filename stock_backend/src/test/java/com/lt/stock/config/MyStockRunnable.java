package com.lt.stock.config;

import com.lt.stock.service.StockTimerService;

import java.util.Map;

/**
 * @description: 自定义线程任务对象
 * @author: ~Teng~
 * @date: 2023/1/11 17:49
 */
public class MyStockRunnable implements Runnable {

    /**
     * 携带的任务信息,任务拒绝时，使用
     */
    private Map<String, Object> infos;

    private StockTimerService stockTimerService;

    public MyStockRunnable(Map<String, Object> infos, StockTimerService stockTimerService) {
        this.infos = infos;
        this.stockTimerService = stockTimerService;
    }

    @Override
    public void run() {
        stockTimerService.stockRtInto();
    }

    public Map<String, Object> getInfos() {
        return infos;
    }
}
