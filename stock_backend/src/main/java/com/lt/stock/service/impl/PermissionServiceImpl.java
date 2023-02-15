package com.lt.stock.service.impl;

import com.google.common.collect.Lists;
import com.lt.stock.pojo.entity.SysPermission;
import com.lt.stock.pojo.vo.PermissionRespNodeVo;
import com.lt.stock.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/2/14 18:46
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Override
    public List<PermissionRespNodeVo> getTree(List<SysPermission> permissions, String pid, boolean isOnlyMenuType) {
        ArrayList<PermissionRespNodeVo> list = Lists.newArrayList();
        if (CollectionUtils.isEmpty(permissions)) {
            return list;
        }
        for (SysPermission permission : permissions) {
            if (permission.getPid().equals(pid)) {
                if (permission.getType().intValue() != 3 || !isOnlyMenuType) {
                    PermissionRespNodeVo permissionRespNodeVo = new PermissionRespNodeVo();
                    permissionRespNodeVo.setId(permission.getId());
                    permissionRespNodeVo.setTitle(permission.getTitle());
                    permissionRespNodeVo.setIcon(permission.getIcon());
                    permissionRespNodeVo.setPath(permission.getUrl());
                    permissionRespNodeVo.setName(permission.getName());
                    permissionRespNodeVo.setChildren(getTree(permissions, permission.getId(), isOnlyMenuType));
                    list.add(permissionRespNodeVo);
                }
            }
        }
        return list;
    }
}
