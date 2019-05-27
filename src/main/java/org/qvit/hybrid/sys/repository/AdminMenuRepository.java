package org.qvit.hybrid.sys.repository;

import java.util.List;

import org.qvit.hybrid.sys.entity.AdminMenu;
import org.qvit.hybrid.sys.mapper.AdminMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminMenuRepository {

    @Autowired
    private AdminMenuMapper adminMenuMapper;

    public void save(AdminMenu dto) {
        adminMenuMapper.insert(dto);
    }

    public AdminMenu findById(Long id) {
        return adminMenuMapper.selectByPrimaryKey(id);
    }

    public void update(AdminMenu menu) {
        adminMenuMapper.updateByPrimaryKey(menu);        
    }

    public List<AdminMenu> findAll() {
       return adminMenuMapper.selectAll();
    }

    public List<AdminMenu> findList(AdminMenu menu) {
        return adminMenuMapper.findList(menu);
    }

    public List<AdminMenu> findListByIds(List<Long> ids) {
        return adminMenuMapper.findListByIds(ids);
    }

    public void deleteById(Long menuId) {
        adminMenuMapper.deleteByPrimaryKey(menuId);        
    }

}
