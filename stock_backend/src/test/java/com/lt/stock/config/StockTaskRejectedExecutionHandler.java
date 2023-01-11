package com.lt.stock.config;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 自定义拒绝策略
 * @author: ~Teng~
 * @date: 2023/1/11 17:48
 */

@Slf4j
public class StockTaskRejectedExecutionHandler implements RejectedExecutionHandler {
    /**
     * 具体执行拒绝策略的方法
     *
     * @param r        当前封装的任务对象
     * @param executor 线程池对象
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (r instanceof MyStockRunnable) {
            MyStockRunnable r2 = ((MyStockRunnable) r);
            Map<String, Object> infos = r2.getInfos();
            log.info("出现的异常的任务信息：{}", infos);
        }
    }
}
