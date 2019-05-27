package org.qvit.hybrid.sys.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.qvit.hybrid.enums.InvalidStatus;
import org.qvit.hybrid.sys.dto.AdminMenuDto;
import org.qvit.hybrid.sys.entity.AdminMenu;
import org.qvit.hybrid.sys.entity.AdminRoleMenu;
import org.qvit.hybrid.sys.repository.AdminMenuRepository;
import org.qvit.hybrid.sys.repository.AdminRoleMenuRepository;
import org.qvit.hybrid.utils.BaseEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class AdminMenuService {
    
    private static Logger log = LoggerFactory.getLogger(AdminMenuService.class);


    @Autowired
    private AdminMenuRepository adminMenuRepository;

    @Autowired
    private AdminRoleMenuRepository adminRoleMenuRepository;

    private LoadingCache<String, List<AdminMenuDto>> cache = CacheBuilder.newBuilder().maximumSize(2).expireAfterAccess(600L, TimeUnit.SECONDS)
            .refreshAfterWrite(60L, TimeUnit.SECONDS).build(new MenuChche());

    public void save(AdminMenuDto dto) {
        AdminMenu menu = convert(dto);
        BaseEntityUtil.bindNewBaseEntity(menu);
        adminMenuRepository.save(menu);
    }

    public void update(AdminMenuDto dto) {
        AdminMenu menu = adminMenuRepository.findById(dto.getId());
        menu.setMenuName(dto.getMenuName());
        menu.setMenuUrl(dto.getMenuUrl());
        menu.setStatus(dto.getStatus().getValue());
        BaseEntityUtil.bindUpdateBaseEntity(menu);
        adminMenuRepository.update(menu);
    }

    public List<AdminMenuDto> findAll() {
        List<AdminMenu> menus = adminMenuRepository.findAll();
        List<AdminMenuDto> menuDtos = new ArrayList<AdminMenuDto>();
        if (menus == null) {
            return null;
        }
        for (AdminMenu menu : menus) {
            menuDtos.add(convertDto(menu));
        }
        return menuDtos;
    }

    public List<AdminMenuDto> findList(AdminMenuDto dto) {
        AdminMenu reqMenu = convert(dto);
        List<AdminMenu> menus = adminMenuRepository.findList(reqMenu);
        List<AdminMenuDto> menuDtos = new ArrayList<AdminMenuDto>();
        if (menus == null) {
            return null;
        }
        for (AdminMenu menu : menus) {
            menuDtos.add(convertDto(menu));
        }
        return menuDtos;
    }

    public List<AdminMenuDto> findListByEffective() {
        try {
            return cache.get("1");
        } catch (ExecutionException e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    private class MenuChche extends CacheLoader<String, List<AdminMenuDto>> {

        @Override
        public List<AdminMenuDto> load(String arg0) throws Exception {
            AdminMenu reqMenu = new AdminMenu();
            reqMenu.setStatus(InvalidStatus.EFFECTIVE.getValue());
            List<AdminMenu> menus = adminMenuRepository.findList(reqMenu);
            List<AdminMenuDto> menuDtos = new ArrayList<AdminMenuDto>();
            if (menus == null) {
                return menuDtos;
            }
            for (AdminMenu menu : menus) {
                menuDtos.add(convertDto(menu));
            }
            return menuDtos;
        }

    }

    public List<AdminMenuDto> findListByRole(List<Long> roleIds) {
        List<AdminRoleMenu> list = adminRoleMenuRepository.findListByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Long> ids = new ArrayList<Long>();
        for (AdminRoleMenu roleMenu : list) {
            ids.add(roleMenu.getMenuId());
        }
        List<AdminMenu> menus = adminMenuRepository.findListByIds(ids);
        List<AdminMenuDto> menuDtos = new ArrayList<AdminMenuDto>();
        if (menus == null) {
            return null;
        }
        for (AdminMenu menu : menus) {
            menuDtos.add(convertDto(menu));
        }
        return menuDtos;
    }

    private AdminMenuDto convertDto(AdminMenu menu) {
        AdminMenuDto dto = new AdminMenuDto();
        BeanUtils.copyProperties(menu, dto);
        dto.setStatus(InvalidStatus.getEnum(menu.getStatus()));
        return dto;
    }

    private AdminMenu convert(AdminMenuDto dto) {
        AdminMenu menu = new AdminMenu();
        BeanUtils.copyProperties(dto, menu);
        if (dto.getStatus() != null) {
            menu.setStatus(dto.getStatus().getValue());
        }
        return menu;
    }

    public AdminMenuDto findById(Long parseLong) {
        List<AdminMenu> findListByIds = adminMenuRepository.findListByIds(Arrays.asList(parseLong));
        if (CollectionUtils.isEmpty(findListByIds)) {
            return null;
        }
        return convertDto(findListByIds.get(0));
    }


    public void deleteById(Long menuId) {
        boolean bool = adminRoleMenuRepository.isDistRoleByMenuId(menuId);
        if(bool){
            throw new RuntimeException("菜单已经关联角色，不能直接删除！");
        }
        adminMenuRepository.deleteById(menuId);
    }
}
