package com.lt.stock.mapper;

import com.lt.stock.pojo.entity.StockRtInfo;
import com.lt.stock.pojo.vo.StockUpDownResponseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author teng
 * @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
 * @createDate 2023-01-06 18:23:05
 * @Entity com.lt.stock.pojo.entity.StockRtInfo
 */
@Repository
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    List<StockUpDownResponseVo> getStockUpDownAllLimit(@Param("curTime") Date curTime);

    List<StockUpDownResponseVo> getStockUpDownAll();

    /**
     * 沪深两市涨跌停分时行情数据查询，查询T日每分钟的涨跌停数据（T：当前股票交易日）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param flag      标志 1-涨停 2-跌停
     */
    List<Map> getStockUpDownCount(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("flag") int flag);

    /**
     * 批量插入功能
     */
    int insertBatch(List<StockRtInfo> stockRtInfoList);
}
