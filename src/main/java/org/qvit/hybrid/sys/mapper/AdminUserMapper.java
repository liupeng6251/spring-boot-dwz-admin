package org.qvit.hybrid.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.qvit.hybrid.sys.entity.AdminUser;

import tk.mybatis.mapper.common.Mapper;

public interface AdminUserMapper extends Mapper<AdminUser>{

    @Select("<script> select "
            +" id id,"           
            +" user_name    username ," 
            +" email ,"        
            +" mobile ,"        
            +" department ,"    
            +" Occupation ,"    
            +" Job_Number jobNumber,"    
            +" entry_Date  entryDate,"   
            +" password  ,"     
            +" status  ,"       
            +" create_Date createDate,"   
            +" update_Date   updateDate," 
            +" version"    
            +" from sys_admin_user "
            + " where 1=1 "
            + "<if test='user.email != null'>"
            + " and email=#{user.email}"
            + "</if>"
            + "<if test='user.mobile != null'>"
            + " and mobile=#{user.mobile}"
            + "</if>"
            + " order by id desc"
            + "</script>"
            )
    List<AdminUser> findPage(@Param("user") AdminUser user);

}
