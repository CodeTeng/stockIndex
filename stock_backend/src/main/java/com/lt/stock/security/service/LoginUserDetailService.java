package com.lt.stock.security.service;

import com.google.common.base.Strings;
import com.lt.stock.mapper.SysPermissionMapper;
import com.lt.stock.mapper.SysRoleMapper;
import com.lt.stock.mapper.SysUserMapper;
import com.lt.stock.pojo.entity.SysPermission;
import com.lt.stock.pojo.entity.SysRole;
import com.lt.stock.pojo.entity.SysUser;
import com.lt.stock.pojo.vo.PermissionRespNodeVo;
import com.lt.stock.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/2/14 18:45
 */
@Component
public class LoginUserDetailService implements UserDetailsService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private PermissionService permissionService;

    /**
     * 根据用户名获取用户详情
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser user = this.sysUserMapper.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        // 获取权限集合
        List<SysPermission> permissionList = this.sysPermissionMapper.getPermissionByUserId(user.getId());
        List<String> permsNameList = permissionList.stream().filter(item -> !Strings.isNullOrEmpty(item.getPerms())).map(SysPermission::getPerms)
                .collect(Collectors.toList());
        // 获取角色集合 基于角色鉴权注解需要将角色前追加ROLE_
        List<SysRole> roleList = sysRoleMapper.getRoleByUserId(user.getId());
        // 角色表示需要追加前缀ROLE_
        List<String> roleNameList = roleList.stream().filter(item -> !Strings.isNullOrEmpty(item.getName()))
                .map(item -> "ROLE_" + item.getName()).collect(Collectors.toList());
        List<String> auths = new ArrayList<String>();
        auths.addAll(permsNameList);
        auths.addAll(roleNameList);
        // 转化为数组
        String[] perms = auths.toArray(new String[auths.size()]);
        // 转化为数组，给springSecurity的
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(perms);
        user.setAuthorities(authorityList);
        // 权限树结构，给前端响应
        List<PermissionRespNodeVo> treeNodeVo = permissionService.getTree(permissionList, "0", true);
        user.setMenus(treeNodeVo);
        // 按钮权限集合，给前端响应
        List<String> authBtnPerms = null;
        if (!CollectionUtils.isEmpty(permissionList)) {
            authBtnPerms = permissionList.stream().filter(per -> !Strings.isNullOrEmpty(per.getCode()) && per.getType() == 3)
                    .map(SysPermission::getCode).collect(Collectors.toList());
        }
        user.setPermissions(authBtnPerms);
        return user;
    }
}
