package com.lt.stock.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lt.stock.common.PageResult;
import com.lt.stock.common.Response;
import com.lt.stock.common.enums.ResponseCode;
import com.lt.stock.mapper.StockBlockRtInfoMapper;
import com.lt.stock.mapper.StockMarketIndexInfoMapper;
import com.lt.stock.mapper.StockRtInfoMapper;
import com.lt.stock.pojo.StockInfoConfig;
import com.lt.stock.pojo.vo.InnerMarketResponseVo;
import com.lt.stock.pojo.vo.StockBlockResponseVo;
import com.lt.stock.pojo.vo.StockExcelResponseVo;
import com.lt.stock.pojo.vo.StockUpDownResponseVo;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
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
        // TODO mock 数据，后续大盘数据实时拉去，将该行注释掉 传入的日期秒必须为0
        String mockDate = "20211221143000";
        Date date = DateTime.parse(mockDate, DateTimeFormat.forPattern("yyyyMMddHHmmss")).toDate();
        // 2. 调用 mapper 返回数据
        List<StockBlockResponseVo> list = stockBlockRtInfoMapper.getStockBlockRtInfoAllLimit(date);
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
}
