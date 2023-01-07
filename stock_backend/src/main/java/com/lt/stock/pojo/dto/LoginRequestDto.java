package com.lt.stock.pojo.dto;

import lombok.Data;

/**
 * @description: 登录请求 DTO
 * @author: ~Teng~
 * @date: 2023/1/6 18:41
 */
@Data
public class LoginRequestDto {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 保存redis随机码的key
     */
    private String rkey;

    /**
     * 验证码
     */
    private String code;
}
