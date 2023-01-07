package com.lt.stock.controller;

import com.lt.stock.common.PageResult;
import com.lt.stock.common.Response;
import com.lt.stock.common.enums.ResponseCode;
import com.lt.stock.pojo.vo.InnerMarketResponseVo;
import com.lt.stock.pojo.vo.StockBlockResponseVo;
import com.lt.stock.pojo.vo.StockUpDownResponseVo;
import com.lt.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 获取沪深两市个股最新交易数据，并按涨幅降序排序查询前10条数据
     */
    @GetMapping("/stock/increase")
    public Response<List<StockUpDownResponseVo>> getStockUpDownAllLimit() {
        return stockService.getStockUpDownAllLimit();
    }

    /**
     * 沪深两市个股行情列表查询 ,以时间顺序和涨幅分页查询
     *
     * @param page     当前页
     * @param pageSize 每页大小
     */
    @GetMapping("/stock/all")
    public Response<PageResult<StockUpDownResponseVo>> stockPage(Integer page, Integer pageSize) {
        if (page == null || pageSize == null) {
            return Response.error(ResponseCode.DATA_ERROR.getMessage());
        }
        if (page <= 0 || pageSize <= 0) {
            return Response.error(ResponseCode.DATA_ERROR.getMessage());
        }
        return stockService.stockPage(page, pageSize);
    }

    /**
     * 将指定页数据导出到excel表下
     *
     * @param response response
     * @param page     当前页
     * @param pageSize 每页大小
     */
    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) {
        stockService.stockExport(response, page, pageSize);
    }
}
