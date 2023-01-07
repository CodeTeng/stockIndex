package com.lt.stock;

import com.lt.stock.utils.DateTimeUtil;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/7 12:19
 */
public class TestJodeDate {

    @Test
    public void testJode() {
        Date date = DateTime.now().toDate();
        // Sat Jan 07 12:20:27 CST 2023
        System.out.println(date);
        DateTime dateTime = DateTime.now().withDate(2023, 1, 9);
        // 2023-01-09 12:22:11
        System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        dateTime = DateTime.now().withDate(2023, 1, 9).withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        // 2023-01-09 12:00:00
        System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
        String lastDateString4Stock = DateTimeUtil.getLastDateString4Stock(DateTime.now());
        System.out.println(lastDateString4Stock);
    }

    @Test
    public void testApi() {
        // 获取jode下的当前时间
        DateTime now = DateTime.now();
        // 日期后退指定的时间
        DateTime plusDays = now.plusDays(1);
        // 2023-01-08T12:25:53.584+08:00
        System.out.println(plusDays);
        // 前推指定的时间
        DateTime minusDays = now.minusDays(7);
        // 2022-12-31T12:25:53.584+08:00
        System.out.println(minusDays);
        // 随意指定日期
        DateTime dateTime = now.withMonthOfYear(8);
        // 2023-08-07T12:25:53.584+08:00
        System.out.println(dateTime);
    }
}
