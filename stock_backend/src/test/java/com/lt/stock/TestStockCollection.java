package com.lt.stock;

import com.lt.stock.service.StockTimerTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/9 16:51
 */
@SpringBootTest
public class TestStockCollection {

    @Autowired
    private StockTimerTaskService stockTimerTaskService;

    @Test
    public void testCollectionInner() {
        stockTimerTaskService.collectInnerMarketInfo();
    }

    @Test
    public void testCollectAShareInfo() {
        stockTimerTaskService.collectAShareInfo();
    }

    @Test
    public void testCollectStockSectorRtIndex() {
        stockTimerTaskService.collectStockSectorRtIndex();
    }
}
