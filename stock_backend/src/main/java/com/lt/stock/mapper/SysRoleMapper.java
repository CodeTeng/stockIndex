package com.lt.stock.mapper;

import com.lt.stock.pojo.entity.SysRole;

/**
* @author teng
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2023-01-06 18:23:05
* @Entity com.lt.stock.pojo.entity.SysRole
*/
public interface SysRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

}
