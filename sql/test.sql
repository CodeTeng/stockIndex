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

# 分析：统计国内A股股票的最新数据，根据涨幅排序取前10
# 方式1：根据日期和涨幅降序排序取前10即可
select sri.trade_amount                                            as tradeAmt,
       sri.pre_close_price                                         as preClosePrice,
       (sri.max_price - sri.min_price) / sri.pre_close_price       as amplitude,
       sri.stock_code                                              as code,
       sri.stock_name                                              as name,
       date_format(sri.cur_time, '%Y%m%d')                         as curDate,
       sri.trade_volume                                            as tradeVol,
       (sri.cur_price - sri.pre_close_price)                       as increase,
       (sri.cur_price - sri.pre_close_price) / sri.pre_close_price as upDown,
       sri.cur_price                                               as tradePrice
from stock_rt_info sri
order by sri.cur_time desc, upDown desc
limit 10;
# 分析：方案1存在的问题：国内A股股票的最新数据等价于查询最近最新股票交易时间
# 点下的数据，同时排序时是全表排序计算的，然后排序完毕后，在筛选出前10条记录
# -----》大表全表排序，取前10---》先根据股票时间点过滤，过滤出的小结果集，然后再将这个结果集排序，取10
# 方案2：【推荐】
# 核心思想：先根据业务推出过滤条件，然后根据过滤条件得到小结果集，然后再将这个小的结果集进行排序运算取前10；
select sri.trade_amount                                            as tradeAmt,
       sri.pre_close_price                                         as preClosePrice,
       (sri.max_price - sri.min_price) / sri.pre_close_price       as amplitude,
       sri.stock_code                                              as code,
       sri.stock_name                                              as name,
       date_format(sri.cur_time, '%Y%m%d')                         as curDate,
       sri.trade_volume                                            as tradeVol,
       (sri.cur_price - sri.pre_close_price)                       as upDown,
       (sri.cur_price - sri.pre_close_price) / sri.pre_close_price as increase,
       sri.cur_price                                               as tradePrice
from stock_rt_info as sri
where sri.cur_time = '2021-12-27 09:47:00'
order by upDown desc
limit 10;

select sri.cur_time,
       (sri.cur_price - sri.pre_close_price) / sri.pre_close_price as increase
from stock_rt_info sri
where date_format(sri.cur_time, '%Y%m%d%H%i') = date_format('2021-12-19 09:57:00', '%Y%m%d%H%i')
having increase >= 0.1;

select sri.cur_time,
       (sri.cur_price - sri.pre_close_price) / sri.pre_close_price as updown
from stock_rt_info sri
where sri.cur_time between '2021-12-19 09:30:00' and '2021-12-19 15:00:00'
having updown >= 0.1;

select date_format(tmp.cur_time, '%Y%m%d%H%i') as time,
       count(*)                                as count
from (select sri.cur_time,
             (sri.cur_price - sri.pre_close_price) / sri.pre_close_price as updown
      from stock_rt_info sri
      where sri.cur_time between '2021-12-19 09:30:00' and '2021-12-19 15:00:00'
      having updown >= 0.1) as tmp
group by tmp.cur_time
order by tmp.cur_time asc;

# 统计国内A股大盘T日和T-1日成交量对比功能（成交量为沪市和深市成交量之和）
# 统计 T 日 如果对索引字段使用了函数 会导致索引失效
# 数据库的核心工作：1、存储数据 2、索引 不擅长复杂逻辑处理  我们应该把复杂的逻辑处理放在 service 层里面去处理
select date_format(smii.cur_time, '%Y%m%d%H%i') as time,
       sum(smii.trade_volume)                   as count
from stock_market_index_info as smii
where smii.mark_Id in ('s_sh000001', 's_sz399001')
  and smii.cur_time between '2022-01-03 09:30:00' and '2022-01-03 14:40:00'
group by smii.cur_time
order by time;
# 统计 T-1 日
select date_format(smii.cur_time, '%Y%m%d%H%i') as time,
       sum(smii.trade_volume)                   as count
from stock_market_index_info as smii
where smii.mark_Id in ('s_sh000001', 's_sz399001')
  and smii.cur_time between '2022-01-02 09:30:00' and '2022-01-02 14:40:00'
group by smii.cur_time
order by time;

# 统计指定股票 T 日每分钟的交易数据
select date_format(sri.cur_time, '%Y%m%d%H%i') as date,
       sri.trade_amount                        as tradeAmt,
       sri.stock_code                          as code,
       sri.min_price                           as lowPrice,
       sri.pre_close_price                     as preClosePrice,
       sri.stock_name                          as name,
       sri.max_price                           as highPrice,
       sri.open_price                          as openPrice,
       sri.trade_volume                        as tradeVol,
       sri.cur_price                           as tradePrice
from stock_rt_info sri
where sri.cur_time between '2021-12-30 09:30:00' and '2021-12-30 15:00:00'
  and sri.stock_code = '600000';
