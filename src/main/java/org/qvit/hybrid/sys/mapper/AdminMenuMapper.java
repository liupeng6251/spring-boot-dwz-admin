package org.qvit.hybrid.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.qvit.hybrid.sys.entity.AdminMenu;

import tk.mybatis.mapper.common.Mapper;

public interface AdminMenuMapper extends Mapper<AdminMenu>{

    @Select("<script>"                                        
            +" select id,menu_name menuName,menu_url menuUrl,status,create_date createDate,update_Date updateDate,version,level,parent_Id parentId "
            + " from sys_admin_menu "
            + " where 1=1 "
            + "<if test='menu.id != null'>"
            + " and id=#{menu.id} "
            + "</if>"
            + "<if test='menu.menuName != null'>"
            + " and menu_name=#{menu.menuName}"
            + "</if> "
            + "<if test='menu.level != null'>"
            + " and level=#{menu.level} "
            + "</if> "
            + "<if test='menu.parentId != null'>"
            + " and parent_Id=#{menu.parentId} "
            + "</if> "
            + "<if test='menu.status != null'>"
            + " and status=#{menu.status} "
            + "</if> "
            + " order by parent_Id,id  "
            + "</script>"
            )
    List<AdminMenu> findList(@Param("menu") AdminMenu menu);

    @Select("<script>"
            +" select id,menu_name menuName,menu_url menuUrl,status,create_date createDate,update_Date updateDate,version,level,parent_Id parentId "
            + " from sys_admin_menu "
            + " where id in"
            + "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>"
            + " #{item} "
            + "</foreach>"
            + "</script>")
    List<AdminMenu> findListByIds(List<Long> ids);

}
