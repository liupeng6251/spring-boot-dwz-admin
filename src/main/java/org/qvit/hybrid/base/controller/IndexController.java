package org.qvit.hybrid.base.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.qvit.hybrid.base.vo.AdminMenuVo;
import org.qvit.hybrid.sys.dto.AdminMenuDto;
import org.qvit.hybrid.sys.service.AdminMenuService;
import org.qvit.hybrid.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private AdminMenuService adminMenuService;

    @RequestMapping("/")
    public String defaultUrl(Model model, HttpServletRequest req) {
        return postIndex(model);
    }

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest req) {
        return postIndex(model);
    }

    private String postIndex(Model model) {
        List<AdminMenuDto> menus = adminMenuService.findListByEffective();
        List<Long> permissionCode = UserUtils.getPermissionCode();
        System.err.println(permissionCode);
        List<AdminMenuVo> menuVos = filter(menus, permissionCode);
        model.addAttribute("menus", menuVos);
        return "index";
    }

    private List<AdminMenuVo> filter(List<AdminMenuDto> menus, List<Long> permissionCode) {
        List<AdminMenuVo> level1 = new ArrayList<>();
        if (CollectionUtils.isEmpty(permissionCode)) {
            return level1;
        }
        List<AdminMenuVo> level2 = new ArrayList<>();
        for (AdminMenuDto dto : menus) {
            if (!permissionCode.contains(dto.getId())) {
               continue;
            }
            if (dto.getLevel().equals(1)) {
                AdminMenuVo vo = converVO(dto);
                level1.add(vo);
            } else if (dto.getLevel().equals(2)) {
                AdminMenuVo vo = converVO(dto);
                level2.add(vo);
                for (AdminMenuVo parentVo : level1) {
                    if (parentVo.getId().equals(vo.getParentId())) {
                        List<AdminMenuVo> childrens = parentVo.getChildrens();
                        if (childrens == null) {
                            childrens = new ArrayList<>();
                        }
                        childrens.add(vo);
                        parentVo.setChildrens(childrens);
                    }
                }
            } else if (dto.getLevel().equals(3)) {
                AdminMenuVo vo = converVO(dto);
                for (AdminMenuVo parentVo : level2) {
                    if (parentVo.getId().equals(vo.getParentId())) {
                        List<AdminMenuVo> childrens = parentVo.getChildrens();
                        if (childrens == null) {
                            childrens = new ArrayList<>();
                        }
                        childrens.add(vo);
                        parentVo.setChildrens(childrens);
                    }
                }
            }
        }
        return level1;
    }

    private AdminMenuVo converVO(AdminMenuDto dto) {
        AdminMenuVo vo = new AdminMenuVo();
        vo.setLevel(dto.getLevel());
        vo.setMenuName(dto.getMenuName());
        vo.setMenuUrl(dto.getMenuUrl());
        vo.setParentId(dto.getParentId());
        vo.setStatus(dto.getStatus());
        vo.setId(dto.getId());
        return vo;
    }
}
