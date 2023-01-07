package com.lt.stock.mapper;

import com.lt.stock.pojo.entity.StockRtInfo;
import com.lt.stock.pojo.vo.StockUpDownResponseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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
}
