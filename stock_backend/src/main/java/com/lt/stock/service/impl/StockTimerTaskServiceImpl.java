package com.lt.stock.service.impl;

import com.google.common.collect.Lists;
import com.lt.stock.common.enums.Number;
import com.lt.stock.mapper.StockBlockRtInfoMapper;
import com.lt.stock.mapper.StockBusinessMapper;
import com.lt.stock.mapper.StockMarketIndexInfoMapper;
import com.lt.stock.mapper.StockRtInfoMapper;
import com.lt.stock.pojo.StockInfoConfig;
import com.lt.stock.pojo.entity.StockBlockRtInfo;
import com.lt.stock.pojo.entity.StockMarketIndexInfo;
import com.lt.stock.pojo.entity.StockRtInfo;
import com.lt.stock.service.StockTimerTaskService;
import com.lt.stock.utils.DateTimeUtil;
import com.lt.stock.utils.IdWorker;
import com.lt.stock.utils.ParserStockInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @description: 定义采集股票接口实现类
 * @author: ~Teng~
 * @date: 2023/1/9 16:43
 */
@Service
@Slf4j
public class StockTimerTaskServiceImpl implements StockTimerTaskService {

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private StockBusinessMapper stockBusinessMapper;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private ParserStockInfoUtil parserStockInfoUtil;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public void collectInnerMarketInfo() {
        // 1. 定义采集的 url
        String marketUrl = stockInfoConfig.getMarketUrl();
        List<String> innerList = stockInfoConfig.getInner();
        String url = marketUrl + String.join(",", innerList);
        // 2. 调用 restTemplate 采集数据
        // 2.1 请求头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        // 2.2 组装请求对象
        HttpEntity<Objects> entity = new HttpEntity<>(headers);
        // 2.3 发送请求
        String resString = restTemplate.postForObject(url, entity, String.class);
        log.info("当前采集的数据：{}", resString);
        // 获取公告采集时间点（精确到分钟即可）
        Date curDateTime = DateTimeUtil.getDateTimeWithoutSecond(DateTime.now()).toDate();
        // 3. 数据解析
//        var hq_str_s_sh000001="上证指数,3176.0845,18.4480,0.58,2581152,34269377";
//        var hq_str_s_sz399001="深证成指,11450.15,82.415,0.72,360313472,46447335";
        String reg = "var hq_str_(.+)=\"(.+)\";";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(resString);
        List<StockMarketIndexInfo> list = new ArrayList<>();
        while (matcher.find()) {
            // 获取数据 进行封装
            String marketCode = matcher.group(1);
            String[] others = matcher.group(2).split(",");
            String marketName = others[0];
            BigDecimal curPoint = new BigDecimal(others[1]);
            BigDecimal curPrice = new BigDecimal(others[2]);
            BigDecimal upDownRate = new BigDecimal(others[3]);
            Long tradeAmount = Long.valueOf(others[4]);
            Long tradeVol = Long.valueOf(others[5]);
            StockMarketIndexInfo stockMarketIndexInfo =
                    StockMarketIndexInfo.builder().id(idWorker.nextId() + "")
                            .markId(marketCode)
                            .curPoint(curPoint)
                            .markName(marketName)
                            .currentPrice(curPrice)
                            .updownRate(upDownRate)
                            .tradeAccount(tradeAmount)
                            .tradeVolume(tradeVol)
                            .curTime(curDateTime).build();
            list.add(stockMarketIndexInfo);
        }
        log.info("集合长度：{}，内容：{}", list.size(), list);
        int count = stockMarketIndexInfoMapper.insertBatch(list);
        log.info("批量插入了：{}条数据", count);
    }

    @Override
    public void collectAShareInfo() {
        // 1. 获取股票代码
        List<String> secCode = stockBusinessMapper.getAllStockCode();
        // 计算出符合sina命名规范的股票id数据
        secCode = secCode.stream().map(code -> code.startsWith("6") ? "sh" + code : "sz" + code).collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        // 2.2 组装请求对象
        HttpEntity<Objects> entity = new HttpEntity<>(headers);
        // 2. 分片,每次查询20条数据
        Lists.partition(secCode, 20).forEach(list -> {
            // 每个分片的数据开启一个线程异步执行任务
            threadPoolTaskExecutor.execute(() -> {
                // 拼接 url
                String marketUrl = stockInfoConfig.getMarketUrl();
                String url = marketUrl + String.join(",", list);
                // 获取响应数据
                String result = restTemplate.postForObject(url, entity, String.class);
                List<StockRtInfo> infos = parserStockInfoUtil.parser4StockOrMarketInfo(result, Number.Three.getNumber());
                log.info("数据量：{}", infos.size());
                stockRtInfoMapper.insertBatch(infos);
            });
        });
    }

    @Override
    public void collectStockSectorRtIndex() {
        String blockUrl = stockInfoConfig.getBlockUrl();
        String resString = restTemplate.getForObject(blockUrl, String.class);
        List<StockBlockRtInfo> infos = parserStockInfoUtil.parse4StockBlock(resString);
        log.info("板块数据量：{}", infos.size());
        // 分片插入 20为一组
        Lists.partition(infos, 20).forEach(list -> {
            // 每个分片的数据开启一个线程异步执行任务
            threadPoolTaskExecutor.execute(() -> {
                stockBlockRtInfoMapper.insertBatch(list);
            });
        });
    }
}
