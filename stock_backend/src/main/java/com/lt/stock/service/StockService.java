package com.lt.stock.service;

import com.lt.stock.common.PageResult;
import com.lt.stock.common.Response;
import com.lt.stock.pojo.vo.InnerMarketResponseVo;
import com.lt.stock.pojo.vo.StockBlockResponseVo;
import com.lt.stock.pojo.vo.StockUpDownResponseVo;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 获取沪深两市个股最新交易数据，并按涨幅降序排序查询前10条数据
     */
    Response<List<StockUpDownResponseVo>> getStockUpDownAllLimit();

    /**
     * 沪深两市个股行情列表查询 ,以时间顺序和涨幅分页查询
     *
     * @param page     当前页
     * @param pageSize 每页大小
     */
    Response<PageResult<StockUpDownResponseVo>> stockPage(Integer page, Integer pageSize);

    /**
     * 将指定页数据导出到excel表下
     *
     * @param response response
     * @param page     当前页
     * @param pageSize 每页大小
     */
    void stockExport(HttpServletResponse response, Integer page, Integer pageSize);
}
