package org.qvit.hybrid.sys.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.qvit.hybrid.sys.dto.AdminRoleDto;
import org.qvit.hybrid.sys.entity.AdminRole;
import org.qvit.hybrid.sys.entity.AdminRoleMenu;
import org.qvit.hybrid.sys.entity.AdminUserRole;
import org.qvit.hybrid.sys.repository.AdminRoleMenuRepository;
import org.qvit.hybrid.sys.repository.AdminRoleRepository;
import org.qvit.hybrid.sys.repository.AdminUserRoleRepository;
import org.qvit.hybrid.utils.BaseEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AdminRoleService {

    @Autowired
    private AdminRoleRepository adminRoleRepository;

    @Autowired
    private AdminRoleMenuRepository adminRoleMenuRepository;

    @Autowired
    private AdminUserRoleRepository adminUserRoleRepository;

    public void save(AdminRoleDto dto) {
        AdminRole role = convert(dto);
        BaseEntityUtil.bindNewBaseEntity(role);
        adminRoleRepository.save(role);
    }

    public List<AdminRoleDto> findAll() {
        List<AdminRole> list = adminRoleRepository.findAll();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<AdminRoleDto> retValue = new ArrayList<>();
        for (AdminRole role : list) {
            retValue.add(convertDto(role));
        }
        return retValue;
    }

    @Transactional
    public void roleRemovalMenu(Long roleId, Long menuId) {
        adminRoleMenuRepository.deleteByRoleIdAndMenuId(roleId, menuId);

    }

    @Transactional
    public void removalMenuById(Long id) {
        adminRoleMenuRepository.deleteById(id);

    }

    public List<AdminRoleDto> findListByUserId(Long userId) {
        List<AdminUserRole> list = adminUserRoleRepository.findListByUserId(userId);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Long> roleIds = new ArrayList<>();
        for (AdminUserRole userRole : list) {
            roleIds.add(userRole.getRoleId());
        }
        List<AdminRole> results = adminRoleRepository.findByIds(roleIds);
        if (CollectionUtils.isEmpty(results)) {
            return null;
        }
        List<AdminRoleDto> retValue = new ArrayList<>();
        for (AdminRole role : results) {
            retValue.add(convertDto(role));
        }
        return retValue;
    }

    private AdminRoleDto convertDto(AdminRole role) {
        AdminRoleDto dto = new AdminRoleDto();
        dto.setCreateDate(role.getCreateDate());
        dto.setId(role.getId());
        dto.setRoleDesc(role.getRoleDesc());
        dto.setRoleName(role.getRoleName());
        dto.setUpdateDate(role.getUpdateDate());
        dto.setVersion(role.getVersion());
        return dto;
    }

    private AdminRole convert(AdminRoleDto dto) {
        AdminRole role = new AdminRole();
        role.setId(dto.getId());
        role.setRoleDesc(dto.getRoleDesc());
        role.setRoleName(dto.getRoleName());
        return role;
    }

    @Transactional
    public void changeMenus(Long parseLong, List<String> asList) {
        adminRoleMenuRepository.deleteByRoleIds(Arrays.asList(parseLong));
        if (CollectionUtils.isEmpty(asList)) {
            return;
        }
        List<AdminRoleMenu> roles = new ArrayList<>();
        for(String menu:asList){
            if(StringUtils.isEmpty(menu)){
                continue;
            }
            AdminRoleMenu role= new AdminRoleMenu();
            role.setMenuId(Long.parseLong(menu));
            role.setRoleId(parseLong);
            BaseEntityUtil.bindNewBaseEntity(role);
            roles.add(role);
        }
        adminRoleMenuRepository.save(roles);
    }

    public void deleteById(Long parseLong) {
        List<AdminRoleMenu> roles = adminRoleMenuRepository.findListByRoleIds(Arrays.asList(parseLong));
        if(CollectionUtils.isNotEmpty(roles)){
            throw  new RuntimeException("已经配置菜单！请先清空菜单再删除");
        }
        adminRoleRepository.deleteById(parseLong);
    }
}
