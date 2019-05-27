package org.qvit.hybrid.sys.mapper;

import org.apache.ibatis.annotations.Select;
import org.qvit.hybrid.sys.entity.AdminRoleMenu;

import tk.mybatis.mapper.common.Mapper;

public interface AdminRoleMenuMapper extends Mapper<AdminRoleMenu>{


    @Select("select count(id)"
            + " from sys_role_menu where menu_id=#{value}")
    Long countDistRoleByMenuId(Long menuId);

}
