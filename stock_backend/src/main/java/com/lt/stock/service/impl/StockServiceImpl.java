package com.lt.stock.service.impl;

import com.lt.stock.common.Response;
import com.lt.stock.mapper.StockBlockRtInfoMapper;
import com.lt.stock.mapper.StockMarketIndexInfoMapper;
import com.lt.stock.pojo.StockInfoConfig;
import com.lt.stock.pojo.vo.InnerMarketResponseVo;
import com.lt.stock.pojo.vo.StockBlockResponseVo;
import com.lt.stock.service.StockService;
import com.lt.stock.utils.DateTimeUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/6 18:27
 */
@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Override
    public Response<List<InnerMarketResponseVo>> getInnerIndexAll() {
        // 1. 获取国内大盘的id集合
        List<String> innerList = stockInfoConfig.getInner();
        // 2. 获取最近最新的股票有效交易日
        Date date = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        // TODO mock 数据，后续大盘数据实时拉去，将该行注释掉 传入的日期秒必须为0
        String mockDate = "20220103111500";
        date = DateTime.parse(mockDate, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        // 3. 调用mapper查询指定日期下对应的国内大盘数据
        List<InnerMarketResponseVo> list = stockMarketIndexInfoMapper.getMarketInfo(innerList, date);
        return Response.ok(list);
    }

    @Override
    public Response<List<StockBlockResponseVo>> getStockBlockRtInfoAllLimit() {
        // 1. 获取最近最新的股票有效交易日
        // TODO mock 数据，后续大盘数据实时拉去，将该行注释掉 传入的日期秒必须为0
        String mockDate = "20211221143000";
        Date date = DateTime.parse(mockDate, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        // 2. 调用 mapper 返回数据
        List<StockBlockResponseVo> list = stockBlockRtInfoMapper.getStockBlockRtInfoAllLimit(date);
        return Response.ok(list);
    }
}
