package org.qvit.hybrid.sys.repository;

import java.util.List;

import org.qvit.hybrid.sys.entity.AdminRole;
import org.qvit.hybrid.sys.mapper.AdminRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.entity.Example;

@Repository
public class AdminRoleRepository {
    
    @Autowired
    private AdminRoleMapper adminRoleMapper;

    public void save(AdminRole role) {
        adminRoleMapper.insert(role);
    }

    public List<AdminRole> findAll() {
        return adminRoleMapper.selectAll();
    }

    public List<AdminRole> findByIds(List<Long> ids) {
        Example example = new Example(AdminRole.class);
        example.createCriteria().andIn("id", ids);
        return adminRoleMapper.selectByExample(example);
    }

    public void deleteById(Long parseLong) {
        adminRoleMapper.deleteByPrimaryKey(parseLong);        
    }

}
