package org.qvit.hybrid.sys.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.qvit.hybrid.base.vo.AdminMenuVo;
import org.qvit.hybrid.sys.dto.AdminMenuDto;
import org.qvit.hybrid.sys.dto.AdminRoleDto;
import org.qvit.hybrid.sys.service.AdminMenuService;
import org.qvit.hybrid.sys.service.AdminRoleService;
import org.qvit.hybrid.utils.ResponseJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system/role")
public class AdminRoleController {
    
    @Autowired
    private AdminRoleService adminRoleService;
    
    @Autowired
    private AdminMenuService adminMenuService;

    @RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Model model, HttpServletRequest req) {
        List<AdminRoleDto> roles = adminRoleService.findAll();
        model.addAttribute("roles", roles);
        return "system/role/list";
    }
    
    @RequestMapping(value = "add", method = {RequestMethod.POST })
    @ResponseBody
    public String addSave( Model model, HttpServletRequest req) {
        String name=req.getParameter("roleName");
        String desc=req.getParameter("roleDesc");
        AdminRoleDto dto = new AdminRoleDto();
        dto.setRoleName(name);
        dto.setRoleDesc(desc);
        adminRoleService.save(dto);
        return ResponseJsonUtils.getSuccessMsg("添加成功！", "/system/role/list","closeCurrent",null,null);
    }
    @RequestMapping(value = "add", method = {RequestMethod.GET })
    public String add( Model model, HttpServletRequest req) {
        return "system/role/add";
    }
    @RequestMapping(value = "addMenu", method = {RequestMethod.GET })
    public String addMenu( Model model, HttpServletRequest req) {
        String id = req.getParameter("id");
        AdminMenuDto dto= new AdminMenuDto();
        dto.setLevel(1);
        List<AdminMenuDto> menus = adminMenuService.findAll();
        List<AdminMenuDto> selectList = adminMenuService.findListByRole(Arrays.asList(Long.parseLong(id)));
        model.addAttribute("menus",filter(menus));
        model.addAttribute("selectList", getIds(selectList));
        model.addAttribute("userId", id);
        return "system/role/addMenu";
    }
    private List<Long> getIds(List<AdminMenuDto> selectList) {
    	ArrayList<Long> list = new ArrayList<>();
    	if(CollectionUtils.isEmpty(selectList)){
    		return list;
    	}
    	for(AdminMenuDto dto:selectList){
    		list.add(dto.getId());
    	}
		return list;
	}

	@RequestMapping(value = "saveMenu", method = RequestMethod.POST)
    @ResponseBody
    public String saveMenu(Model model, HttpServletRequest req) {
        String id=req.getParameter("userId");
        String [] menuIds=req.getParameterValues("name");
        List<String> ids=null;
        if(menuIds!=null){
            ids=Arrays.asList(menuIds);
        }
        adminRoleService.changeMenus(Long.parseLong(id),ids);
        return ResponseJsonUtils.getSuccessMsg("保存成功！", "/system/role/saveMenu");
    }
    
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Model model, HttpServletRequest req) {
        String id=req.getParameter("id");
        adminRoleService.deleteById(Long.parseLong(id));
        return ResponseJsonUtils.getSuccessMsg("保存成功！", "/system/role/list");
    }
    private List<AdminMenuVo> filter(List<AdminMenuDto> menus) {
        List<AdminMenuVo> level1 = new ArrayList<>();
        List<AdminMenuVo> level2 = new ArrayList<>();
        for (AdminMenuDto dto : menus) {
        	  AdminMenuVo vo = converVO(dto);
            if (dto.getLevel().equals(1)) {
                level1.add(vo);
            } else if (dto.getLevel().equals(2)) {
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
            }else if(dto.getLevel().equals(3)){
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
