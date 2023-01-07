package com.lt.stock.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/7 19:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@HeadRowHeight(value = 35) // 表头行高
@ContentRowHeight(value = 25) // 内容行高
@ColumnWidth(value = 50) // 列宽
public class User implements Serializable {
    @ExcelProperty(value = {"用户基本信息", "用户名"}, index = 1)
    // @ExcelIgnore
    private String userName;
    @ExcelProperty(value = {"用户基本信息", "年龄"}, index = 2)
    private Integer age;
    @ExcelProperty(value = {"用户基本信息", "地址"}, index = 4)
    private String address;

    /**
     * 注意：日期格式注解由alibaba.excel提供
     */
    @ExcelProperty(value = {"用户基本信息", "生日"}, index = 3)
    @DateTimeFormat("yyyy/MM/dd HH:mm")
    private Date birthday;
}
