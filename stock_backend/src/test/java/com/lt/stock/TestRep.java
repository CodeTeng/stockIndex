package com.lt.stock;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/9 16:17
 */
public class TestRep {

    @Test
    public void testRep1() {
        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        String regex = "(\\D*)(\\d+)(.*)";
        // 创建 Pattern 对象
        Pattern p = Pattern.compile(regex);
        // 创建 matcher 对象
        Matcher matcher = p.matcher(line);
        if (matcher.find()) {
            System.out.println("Found value: " + matcher.group(0));
            System.out.println("Found value: " + matcher.group(1));
            System.out.println("Found value: " + matcher.group(2));
            System.out.println("Found value: " + matcher.group(3));
        } else {
            System.out.println("NO MATCH");
        }
    }
}
