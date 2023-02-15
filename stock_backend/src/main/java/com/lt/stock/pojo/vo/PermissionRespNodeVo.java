package com.lt.stock.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/2/14 19:20
 */
@Data
public class PermissionRespNodeVo {
    /**
     * 主键
     */
    private String id;

    /**
     * 菜单权限名称
     */
    private String title;

    /**
     * 菜单图标(侧边导航栏图标)
     */
    private String icon;

    /**
     * 访问地址URL
     */
    private String path;

    /**
     * name与前端vue路由name约定一致
     */
    private String name;

    /**
     * 菜单数据结构
     */
    private List<PermissionRespNodeVo> children;
}
