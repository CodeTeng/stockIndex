package com.lt.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lt.stock.common.PageResult;
import com.lt.stock.common.Response;
import com.lt.stock.common.enums.Number;
import com.lt.stock.common.enums.ResponseCode;
import com.lt.stock.mapper.StockBlockRtInfoMapper;
import com.lt.stock.mapper.StockMarketIndexInfoMapper;
import com.lt.stock.mapper.StockRtInfoMapper;
import com.lt.stock.pojo.StockInfoConfig;
import com.lt.stock.pojo.vo.*;
import com.lt.stock.service.StockService;
import com.lt.stock.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/6 18:27
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {
    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

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
        if (CollectionUtils.isEmpty(list)) {
            return Response.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return Response.ok(list);
    }

    @Override
    public Response<List<StockBlockResponseVo>> getStockBlockRtInfoAllLimit() {
        // 1. 获取最近最新的股票有效交易日
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        // TODO mock 数据，后续大盘数据实时拉去，将该行注释掉 传入的日期秒必须为0
        lastDate = DateTime.parse("2021-12-21 14:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2. 调用 mapper 返回数据
        List<StockBlockResponseVo> list = stockBlockRtInfoMapper.getStockBlockRtInfoAllLimit(lastDate);
        if (CollectionUtils.isEmpty(list)) {
            return Response.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return Response.ok(list);
    }

    @Override
    public Response<List<StockUpDownResponseVo>> getStockUpDownAllLimit() {
        // 1.获取最近最新的股票有效交易日
        // TODO mock 数据，后续大盘数据实时拉去，将该行注释掉 传入的日期秒必须为0
        String mockStr = "2021-12-27 09:47:00";
        Date curDateTime = DateTime.parse(mockStr, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2. 返回数据
        List<StockUpDownResponseVo> list = stockRtInfoMapper.getStockUpDownAllLimit(curDateTime);
        if (CollectionUtils.isEmpty(list)) {
            return Response.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return Response.ok(list);
    }

    @Override
    public Response<PageResult<StockUpDownResponseVo>> stockPage(Integer page, Integer pageSize) {
        // 1. 设置分页参数
        PageHelper.startPage(page, pageSize);
        // 2. 通过 mapper 查询数据
        List<StockUpDownResponseVo> list = stockRtInfoMapper.getStockUpDownAll();
        if (CollectionUtils.isEmpty(list)) {
            return Response.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        // 3. 封装到分页对象中
        PageInfo<StockUpDownResponseVo> pageInfo = new PageInfo<>(list);
        PageResult<StockUpDownResponseVo> pageResult = new PageResult<>(pageInfo);
        // 4. 返回
        return Response.ok(pageResult);
    }

    @Override
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("stockRt", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 分页查询股票数据
            PageHelper.startPage(page, pageSize);
            List<StockUpDownResponseVo> list = stockRtInfoMapper.getStockUpDownAll();
            List<StockExcelResponseVo> stockExcelResponseVos = list.stream().map(info -> {
                StockExcelResponseVo stockExcelResponseVo = new StockExcelResponseVo();
                BeanUtils.copyProperties(info, stockExcelResponseVo);
                return stockExcelResponseVo;
            }).collect(Collectors.toList());
            // 导出
            EasyExcel.write(response.getOutputStream(), StockExcelResponseVo.class).sheet("股票数据").doWrite(stockExcelResponseVos);
        } catch (IOException e) {
            log.info("股票excel数据导出异常，当前页：{}，每页大小：{}，异常信息：{}", page, pageSize, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Response<Map> getStockUpDownCount() {
        // 1. 获取股票最近的有效交易日期,精确到秒
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        // 2. 获取开盘时间和收盘时间
        Date openTime = DateTimeUtil.getOpenDate(curDateTime).toDate();
        Date closeTime = DateTimeUtil.getCloseDate(curDateTime).toDate();
        // TODO mock 数据
        openTime = DateTime.parse("2021-12-19 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        closeTime = DateTime.parse("2021-12-19 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 3. 查询涨停和跌停的统计数
        List<Map> upList = stockRtInfoMapper.getStockUpDownCount(openTime, closeTime, Number.One.getNumber());
        if (CollectionUtils.isEmpty(upList)) {
            upList = new ArrayList<>();
        }
        List<Map> downList = stockRtInfoMapper.getStockUpDownCount(openTime, closeTime, Number.Two.getNumber());
        if (CollectionUtils.isEmpty(downList)) {
            downList = new ArrayList<>();
        }
        // 4. 封装 Map
        Map<String, List> map = new HashMap<>(2);
        map.put("upList", upList);
        map.put("downList", downList);
        // 5. 返回数据
        return Response.ok(map);
    }

    @Override
    public Response<Map> getStockTradeAccountCount() {
        // 1. 获取 T 日和 T-1 日的开始时间和结束时间
        // 1.1 获取最近股票有效交易时间点 --- T 日的时间范围
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        DateTime openDateTime = DateTimeUtil.getOpenDate(lastDateTime);
        Date startTime4T = openDateTime.toDate();
        Date endTime4T = lastDateTime.toDate();
        // TODO mock 数据
        startTime4T = DateTime.parse("2022-01-03 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        endTime4T = DateTime.parse("2022-01-03 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 1.2 获取 T-1 日的时间范围
        DateTime preLastDateTime = DateTimeUtil.getPreviousTradingDay(lastDateTime);
        DateTime preOpenDateTime = DateTimeUtil.getPreviousTradingDay(openDateTime);
        Date startTime4PreT = preOpenDateTime.toDate();
        Date endTime4PreT = preLastDateTime.toDate();
        // TODO mock 数据
        startTime4PreT = DateTime.parse("2022-01-02 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        endTime4PreT = DateTime.parse("2022-01-02 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2. 获取上证和深证的大盘id
        List<String> marketIds = stockInfoConfig.getInner();
        // 3. 获取 T 日和 T-1 日的交易量数据
        // 3.1 查询 T 日大盘交易统计数据
        List<Map> volList = stockMarketIndexInfoMapper.getStockTradeAccountCount(marketIds, startTime4T, endTime4T);
        if (CollectionUtils.isEmpty(volList)) {
            volList = new ArrayList<>();
        }
        // 3.2 查询 T-1 日大盘交易统计数据
        List<Map> yesVolList = stockMarketIndexInfoMapper.getStockTradeAccountCount(marketIds, startTime4PreT, endTime4PreT);
        if (CollectionUtils.isEmpty(yesVolList)) {
            yesVolList = new ArrayList<>();
        }
        // 4. 封装数据
        Map<String, List> map = new HashMap<>(2);
        map.put("volList", volList);
        map.put("yesVolList", yesVolList);
        // 5. 返回
        return Response.ok(map);
    }

    @Override
    public Response<List<StockMinuteResponseVo>> getStockMinute(String code) {
        // 1. 获取 T 日的开始时间和结束时间
        // 1.1 获取最近的有效交易时间
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        DateTime openDateTime = DateTimeUtil.getOpenDate(lastDateTime);
        // TODO mock 数据
        Date startTime = DateTime.parse("2021-12-30 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        Date endTime = DateTime.parse("2021-12-30 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2. 查询数据
        List<StockMinuteResponseVo> list = stockBlockRtInfoMapper.getStockMinute(code, startTime, endTime);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        // 3. 返回
        return Response.ok(list);
    }

    @Override
    public Response<Map> getStockUpDown() {
        // 1. 获取当前时间下最近的一个股票交易时间 精确到秒
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date date = lastDateTime.toDate();
        // todo mock 数据
        date = DateTime.parse("2022-01-06 09:55:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2. 查询数据
        List<Map> maps = stockBlockRtInfoMapper.getStockUpDown(date);
        if (CollectionUtils.isEmpty(maps)) {
            maps = new ArrayList<>();
        }
        // 2.1 获取股票涨幅区间
        List<String> upDownRange = stockInfoConfig.getUpDownRange();
        // 2.2 将 list 集合下的字符串映射成 Map 对象
        List<Map> finalMaps = maps;
        List<Map> orderMap = upDownRange.stream().map(key -> {
            Optional<Map> titleMap = finalMaps.stream().filter(map -> key.equals(map.get("title"))).findFirst();
            Map tmp = null;
            if (titleMap.isPresent()) {
                tmp = titleMap.get();
            } else {
                tmp = new HashMap(2);
                tmp.put("title", key);
                tmp.put("count", 0);
            }
            return tmp;
        }).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>(2);
        map.put("time", lastDateTime.toString("yyyy-MM-dd HH:mm:ss"));
        map.put("infos", orderMap);
        // 3. 返回
        return Response.ok(map);
    }

    @Override
    public Response<List<StockDayResponseVo>> getStockDay(String code) {
        // 1. 获取当前有效开盘时间
        DateTime lastDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date endDate = lastDateTime.toDate();
        // 前推20天
        Date startDate = lastDateTime.minusDays(20).toDate();
        // todo mock 数据
        startDate = DateTime.parse("2021-12-13 09:32:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        endDate = DateTime.parse("2022-01-02 09:56:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2. 查询 mapper 获取数据
//        List<StockDayResponseVo> list = stockBlockRtInfoMapper.getStockDay(code, startDate, endDate);
        // 改进 为后序分库分表准备
        List<Date> closeDates = stockBlockRtInfoMapper.getCloseDates(code, startDate, endDate);
        List<StockDayResponseVo> list = stockBlockRtInfoMapper.getStockDayByDates(code, closeDates);
        if (CollectionUtils.isEmpty(list)) {
            return Response.error(ResponseCode.NO_RESPONSE_DATA.getMessage());
        }
        return Response.ok(list);
    }
}
