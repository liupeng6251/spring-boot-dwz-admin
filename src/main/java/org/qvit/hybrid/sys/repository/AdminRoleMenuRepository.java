package org.qvit.hybrid.sys.repository;

import java.util.List;

import org.qvit.hybrid.sys.entity.AdminRoleMenu;
import org.qvit.hybrid.sys.mapper.AdminRoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tk.mybatis.mapper.entity.Example;

@Repository
public class AdminRoleMenuRepository {

    @Autowired
    private AdminRoleMenuMapper adminRoleMenuMapper;

    public List<AdminRoleMenu> findListByRoleIds(List<Long> roleIds) {
        Example example = new Example(AdminRoleMenu.class);
        example.createCriteria().andIn("roleId", roleIds);
        return adminRoleMenuMapper.selectByExample(example);
    }

    public void deleteByRoleIdAndMenuId(Long roleId, Long menuId) {
        Example example = new Example(AdminRoleMenu.class);
        example.createCriteria().andEqualTo("roleId", roleId).andEqualTo("menuId", menuId);
        adminRoleMenuMapper.deleteByExample(example);

    }

    public void deleteById(Long id) {
        Example example = new Example(AdminRoleMenu.class);
        example.createCriteria().andEqualTo("id", id);
        adminRoleMenuMapper.deleteByExample(example);

    }

    public void save(List<AdminRoleMenu> roles) {
        for (AdminRoleMenu menu : roles) {
            adminRoleMenuMapper.insertSelective(menu);
        }
    }

    public void deleteByRoleIds(List<Long> asList) {
        Example example = new Example(AdminRoleMenu.class);
        example.createCriteria().andIn("roleId", asList);
        adminRoleMenuMapper.deleteByExample(example);

    }

    public boolean isDistRoleByMenuId(Long menuId) {
        Long count = adminRoleMenuMapper.countDistRoleByMenuId(menuId);
        return count != null && count > 0;
    }

}
