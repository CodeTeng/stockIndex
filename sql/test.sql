# 主表信息
select smii.trade_account                       as tradeAmt,
       smii.mark_Id                             as code,
       smii.mark_name                           as name,
       date_format(smii.cur_time, '%Y%m%d%H%i') as curDate,
       smii.trade_volume                        as tradeVol,
       smii.updown_rate                         as upDown,
       smii.current_price                       as tradePrice
from stock_market_index_info as smii
where smii.mark_Id in ('s_sh000001', 's_sz399001')
  and smii.cur_time = '2022-01-03 11:15:00';

# 然后获取主表信息数据后（数据被压缩）关联日统计表查询
select tmp.*,
       smlp.pre_close_price as preClosePrice,
       smlp.open_price      as openPrice
from (select smii.trade_account                       as tradeAmt,
             smii.mark_Id                             as code,
             smii.mark_name                           as name,
             date_format(smii.cur_time, '%Y%m%d%H%i') as curDate,
             smii.trade_volume                        as tradeVol,
             smii.updown_rate                         as upDown,
             smii.current_price                       as tradePrice
      from stock_market_index_info as smii
      where smii.mark_Id in ('s_sh000001', 's_sz399001')
        and smii.cur_time = '2022-01-03 11:15:00') as tmp
         left join stock_market_log_price as smlp
                   on tmp.code = smlp.market_code and
                      date_format(smlp.cur_date, '%Y%m%d') = date_format('2022-01-03 11:15:00', '%Y%m%d');

# 沪深两市板块分时行情数据查询，以交易时间和交易总金额降序查询，取前10条数据
# 这样写功能没毛病，但是SQL性能有大问题，每次查询都是全表排序，然后再查询，开销非常大
select sbri.company_num                     as companyNum,
       sbri.trade_amount                    as tradeAmt,
       sbri.label                           as code,
       sbri.avg_price                       as avgPrice,
       sbri.block_name                      as name,
       date_format(sbri.cur_time, '%Y%m%d') as curDate,
       sbri.trade_volume                    as tradeVol,
       sbri.updown_rate                     as updownRate
from stock_block_rt_info as sbri
order by sbri.cur_time desc, sbri.trade_volume desc
limit 10;
# 最合理的方式 日期建索引 并且根据最近有效交易日期作为条件查询出数据，然后再为少量的这部分数据排序（尽量避免全表查询）
select sbri.company_num                     as companyNum,
       sbri.trade_amount                    as tradeAmt,
       sbri.label                           as code,
       sbri.avg_price                       as avgPrice,
       sbri.block_name                      as name,
       date_format(sbri.cur_time, '%Y%m%d') as curDate,
       sbri.trade_volume                    as tradeVol,
       sbri.updown_rate                     as updownRate
from stock_block_rt_info as sbri
where sbri.cur_time = '2021-12-21 14:30:00'
order by sbri.trade_volume desc
limit 10;