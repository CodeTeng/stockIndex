package com.lt.stock.mapper;

import com.lt.stock.pojo.entity.StockBlockRtInfo;
import com.lt.stock.pojo.entity.StockRtInfo;
import com.lt.stock.pojo.vo.StockBlockResponseVo;
import com.lt.stock.pojo.vo.StockDayResponseVo;
import com.lt.stock.pojo.vo.StockMinuteResponseVo;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author teng
 * @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
 * @createDate 2023-01-06 18:23:05
 * @Entity com.lt.stock.pojo.entity.StockBlockRtInfo
 */
@Repository
public interface StockBlockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBlockRtInfo record);

    int insertSelective(StockBlockRtInfo record);

    StockBlockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBlockRtInfo record);

    int updateByPrimaryKey(StockBlockRtInfo record);

    List<StockBlockResponseVo> getStockBlockRtInfoAllLimit(@Param("timePoint") Date timePoint);

    /**
     * 统计指定股票T日每分钟的交易数据
     *
     * @param code      股票编码
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    List<StockMinuteResponseVo> getStockMinute(@Param("code") String code, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 统计当前时间下（精确到分钟），股票在各个涨跌区间的数量
     *
     * @param date 当前有效时间
     */
    List<Map> getStockUpDown(@Param("date") Date date);

    /**
     * 个股日K数据查询 ，可以根据时间区间查询数日的K线数据  默认查询历史20天的数据
     *
     * @param code      股票编码
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    List<StockDayResponseVo> getStockDay(@Param("code") String code, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 查询指定时间范围下股票每日的最大日期
     *
     * @param code      股票编码
     * @param startDate 起始时间
     * @param endDate   结束时间
     */
    List<Date> getCloseDates(@Param("code") String code, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 获取指定股票在指定日期下的数据
     *
     * @param code  股票编码
     * @param dates 指定日期集合
     */
    List<StockDayResponseVo> getStockDayByDates(@Param("code") String code, @Param("dates") List<Date> dates);

    /**
     * 板块信息批量插入
     */
    int insertBatch(List<StockBlockRtInfo> list);
}
