package org.qvit.hybrid.sys.repository;

import java.util.List;

import org.qvit.hybrid.sys.entity.AdminUserRole;
import org.qvit.hybrid.sys.mapper.AdminUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.entity.Example;

@Repository
public class AdminUserRoleRepository {
    
    @Autowired
    private  AdminUserRoleMapper adminUserRoleMapper;

    public List<AdminUserRole> findListByUserId(Long userId) {
        Example example = new Example(AdminUserRole.class);
        example.createCriteria().andEqualTo("userId", userId);
        return adminUserRoleMapper.selectByExample(example);
    }

    public void deleteByRoleIdAndUserId(Long roleId, Long userId) {
        
        Example example = new Example(AdminUserRole.class);
        example.createCriteria().andEqualTo("roleId", roleId).andEqualTo("userId",userId);
         adminUserRoleMapper.deleteByExample(example);
        
    }

    public void deleteById(Long id) {
        adminUserRoleMapper.deleteByPrimaryKey(id);
    }

    public void save(List<AdminUserRole> roles) {
       for(AdminUserRole role:roles){
           adminUserRoleMapper.insertSelective(role);
       }
        
    }

    public void deleteByUserId(Long userId) {
        Example example = new Example(AdminUserRole.class);
        example.createCriteria().andEqualTo("userId",userId);
        adminUserRoleMapper.deleteByExample(example);
        
    }

}
