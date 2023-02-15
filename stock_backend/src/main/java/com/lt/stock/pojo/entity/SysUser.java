package com.lt.stock.pojo.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.lt.stock.pojo.vo.PermissionRespNodeVo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户表
 *
 * @author teng
 * @TableName sys_user
 */
@Data
public class SysUser implements UserDetails {
    /**
     * 用户id
     */
    private String id;

    /**
     * 账户
     */
    private String username;

    /**
     * 用户密码密文
     */
    private String password;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 真实名称
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱(唯一)
     */
    private String email;

    /**
     * 账户状态(1.正常 2.锁定 )
     */
    private Integer status;

    /**
     * 性别(1.男 2.女)
     */
    private Integer sex;

    /**
     * 是否删除(1未删除；0已删除)
     */
    private Integer deleted;

    /**
     * 创建人
     */
    private String createId;

    /**
     * 更新人
     */
    private String updateId;

    /**
     * 创建来源(1.web 2.android 3.ios )
     */
    private Integer createWhere;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 组装的是用户拥有的权限对应的前端的侧边菜单栏（不包含按钮）实现动态加载
     */
    private List<PermissionRespNodeVo> menus;

    /**
     * 前端按钮权限表示
     */
    private List<String> permissions;

    /**
     * 权限集合
     */
    private List<GrantedAuthority> authorities;

    /**
     * true:账号未过期
     */
    private boolean accountNonExpired = true;

    /**
     * true:账号未锁定
     */
    private boolean accountNonLocked = true;

    /**
     * true:密码未过期
     */
    private boolean credentialsNonExpired = true;

    /**
     * true:不禁用
     */
    private boolean enabled = true;
}