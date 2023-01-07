package com.lt.stock.pojo.vo;

import lombok.Data;

/**
 * @description: 登录后响应前端的vo
 * @author: ~Teng~
 * @date: 2023/1/6 18:42
 */
@Data
public class LoginResponseVo {
    /**
     * 用户ID
     */
    private String id;

    /**
     * 电话
     */
    private String phone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickName;
}
