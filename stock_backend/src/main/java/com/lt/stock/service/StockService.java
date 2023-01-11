package com.lt.stock.service;

import com.lt.stock.common.PageResult;
import com.lt.stock.common.Response;
import com.lt.stock.pojo.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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

    /**
     * 沪深两市涨跌停分时行情数据查询，查询T日每分钟的涨跌停数据（T：当前股票交易日）
     *
     * @return map:{upList:涨停数据统计，downList:跌停数据统计}
     */
    Response<Map> getStockUpDownCount();

    /**
     * 统计国内A股大盘T日和T-1日成交量对比功能（成交量为沪市和深市成交量之和）
     *
     * @return map:{"volList":
     * [{"count": 3926392,"time": "202112310930"},......],
     * "yesVolList":
     * [{"count": 3926392,"time": "202112310930"},......]}
     */
    Response<Map> getStockTradeAccountCount();

    /**
     * 统计指定股票T日每分钟的交易数据
     *
     * @param code 股票编码
     */
    Response<List<StockMinuteResponseVo>> getStockMinute(String code);

    /**
     * 统计当前时间下（精确到分钟），股票在各个涨跌区间的数量
     */
    Response<Map> getStockUpDown();

    /**
     * 个股日K数据查询 ，可以根据时间区间查询数日的K线数据  默认查询历史20天的数据
     *
     * @param code 股票编码
     */
    Response<List<StockDayResponseVo>> getStockDay(String code);

    /**
     * 外盘指数行情数据查询，根据时间和大盘点数降序排序取前4
     */
    Response<List<OuterMarketResponseVo>> getOuterMarketAll();

    /**
     * 根据输入的个股代码，进行模糊查询，返回证券代码和证券名称
     *
     * @param code 只接受代码模糊查询，不支持文字查询
     */
    Response<List<StockSearchResponseVo> > getStockSearch(String code);
}
