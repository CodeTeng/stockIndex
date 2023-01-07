package com.lt.stock;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lt.stock.pojo.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/7 19:00
 */
public class TestEasyExcel {
    public List<User> init() {
        //组装数据
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAddress("上海" + i);
            user.setUserName("张三" + i);
            user.setBirthday(new Date());
            user.setAge(10 + i);
            users.add(user);
        }
        return users;
    }

    @Test
    public void testWriteExcel() {
        List<User> users = init();
        EasyExcel.write("D:\\MyCode\\JavaProjects\\web\\todayIndex\\stock_parent\\excel\\用户.xls", User.class).sheet("用户信息").doWrite(users);
    }

    @Test
    public void testReadExcel() {
        ArrayList<User> users = new ArrayList<>();
        EasyExcel.read("D:\\MyCode\\JavaProjects\\web\\todayIndex\\stock_parent\\excel\\用户.xls", User.class, new AnalysisEventListener<User>() {
            @Override
            public void invoke(User user, AnalysisContext analysisContext) {
                System.out.println(user);
                users.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("完成。。。。");
            }
        }).sheet().doRead();
        System.out.println(users);
    }
}
