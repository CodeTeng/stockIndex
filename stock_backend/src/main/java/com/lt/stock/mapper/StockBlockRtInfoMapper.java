package com.lt.stock.mapper;

import com.lt.stock.pojo.entity.StockBlockRtInfo;
import com.lt.stock.pojo.vo.StockBlockResponseVo;
import com.lt.stock.pojo.vo.StockMinuteResponseVo;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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
}
