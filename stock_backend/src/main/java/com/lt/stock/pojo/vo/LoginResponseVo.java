package com.lt.stock.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 登录后响应前端的vo
 * @author: ~Teng~
 * @date: 2023/1/6 18:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    /**
     * 组装的是用户拥有的权限对应的前端的侧边菜单栏（不包含按钮）实现动态加载
     */
    private List<PermissionRespNodeVo> menus;

    /**
     * 前端按钮权限表示
     */
    private List<String> permissions;

    /**
     * 认证成功后响应的 jwt token
     */
    private String accessToken;
}
