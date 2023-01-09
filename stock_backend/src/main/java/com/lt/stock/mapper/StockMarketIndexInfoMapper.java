package com.lt.stock.mapper;

import com.lt.stock.pojo.entity.StockMarketIndexInfo;
import com.lt.stock.pojo.vo.InnerMarketResponseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author teng
 * @description 针对表【stock_market_index_info(股票大盘数据详情表)】的数据库操作Mapper
 * @createDate 2023-01-06 18:23:05
 * @Entity com.lt.stock.pojo.entity.StockMarketIndexInfo
 */
@Repository
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    /**
     * 根据大盘 id 和时间查询大盘信息
     *
     * @param marketIds 大盘id集合
     * @param timePoint 时间点
     * @return 大盘信息
     */
    List<InnerMarketResponseVo> getMarketInfo(@Param("marketIds") List<String> marketIds, @Param("timePoint") Date timePoint);

    /**
     * 根据时间范围和指定的大盘id统计每分钟的交易量
     *
     * @param marketIds 股票大盘的编码code集合
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    List<Map> getStockTradeAccountCount(@Param("marketIds") List<String> marketIds, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 批量插入股票大盘数据
     *
     * @param infos 股票数据
     */
    int insertBatch(@Param("infos") List<StockMarketIndexInfo> infos);
}
