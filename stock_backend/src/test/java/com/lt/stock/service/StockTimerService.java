package com.lt.stock.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/11 17:46
 */
@Service
@Slf4j
public class StockTimerService {
    /**
     * 拉取股票服务
     */
    public void stockRtInto() {
        // 模拟网络I/O  1000毫秒
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
