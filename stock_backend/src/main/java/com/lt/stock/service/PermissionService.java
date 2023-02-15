package com.lt.stock.service;

import com.lt.stock.pojo.entity.SysPermission;
import com.lt.stock.pojo.vo.PermissionRespNodeVo;

import java.util.List;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/2/14 18:46
 */
public interface PermissionService {
    List<PermissionRespNodeVo> getTree(List<SysPermission> permissionList, String pid, boolean isOnlyMenuType);
}
