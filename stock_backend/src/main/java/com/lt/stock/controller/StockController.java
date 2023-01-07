package com.lt.stock.controller;

import com.lt.stock.common.Response;
import com.lt.stock.pojo.vo.InnerMarketResponseVo;
import com.lt.stock.pojo.vo.StockBlockResponseVo;
import com.lt.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/6 18:28
 */
@RestController
@RequestMapping("/api/quot")
@CrossOrigin
public class StockController {
    @Autowired
    private StockService stockService;

    /**
     * 获取国内最新大盘指数
     */
    @GetMapping("/index/all")
    public Response<List<InnerMarketResponseVo>> getInnerIndexAll() {
        return stockService.getInnerIndexAll();
    }

    /**
     * 获取沪深两市板块分时行情数据，以交易时间和交易总金额降序查询，取前10条数据
     */
    @GetMapping("/sector/all")
    public Response<List<StockBlockResponseVo>> getStockBlockRtInfoAllLimit() {
        return stockService.getStockBlockRtInfoAllLimit();
    }
}
