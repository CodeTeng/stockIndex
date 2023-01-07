package com.lt.stock.service;

import com.lt.stock.common.Response;
import com.lt.stock.pojo.vo.InnerMarketResponseVo;
import com.lt.stock.pojo.vo.StockBlockResponseVo;

import java.util.List;

/**
 * @description: 定义股票服务接口
 * @author: ~Teng~
 * @date: 2023/1/6 18:26
 */
public interface StockService {
    /**
     * 获取国内最新大盘指数
     */
    Response<List<InnerMarketResponseVo>> getInnerIndexAll();

    /**
     * 获取沪深两市板块分时行情数据，以交易时间和交易总金额降序查询，取前10条数据
     */
    Response<List<StockBlockResponseVo>> getStockBlockRtInfoAllLimit();
}
