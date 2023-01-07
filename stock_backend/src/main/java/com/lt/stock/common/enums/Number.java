package com.lt.stock.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/1/6 20:44
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum Number {
    /**
     * 数字
     */
    One(1), Two(2), Three(3);

    int number;
}
