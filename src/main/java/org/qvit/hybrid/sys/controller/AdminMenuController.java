package org.qvit.hybrid.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.qvit.hybrid.enums.InvalidStatus;
import org.qvit.hybrid.sys.dto.AdminMenuDto;
import org.qvit.hybrid.sys.service.AdminMenuService;
import org.qvit.hybrid.utils.ResponseJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/system/menu")
public class AdminMenuController {

    @Autowired
    private AdminMenuService adminMenuService;
    

    @RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Model model, HttpServletRequest req) {
        AdminMenuDto dto = new AdminMenuDto();
        String parentId = req.getParameter("parentId");
        String level = req.getParameter("level");
        String menuName = req.getParameter("menuName");
        if (StringUtils.isNotEmpty(parentId)) {
            dto.setParentId(Long.parseLong(parentId));
        }
        if (StringUtils.isNotEmpty(level)) {
            dto.setLevel(Integer.parseInt(level));
        }
        if (StringUtils.isNotEmpty(menuName)) {
            dto.setMenuName(menuName);
        }
        List<AdminMenuDto> findList = adminMenuService.findList(dto);
        model.addAttribute("menus", findList);
        model.addAttribute("parentId", parentId);
        model.addAttribute("level", level);
        model.addAttribute("menuName", menuName);
        return "system/menu/list";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add() {
        return "system/menu/add";
    }

    @RequestMapping(value = "change", method = RequestMethod.GET)
    public String changeView(Model model, HttpServletRequest req) {
        String id=req.getParameter("id");
        AdminMenuDto dto = adminMenuService.findById(Long.parseLong(id));
        if (dto == null) {
            throw new RuntimeException("数据有误!");
        }
        model.addAttribute("menu", dto);
        System.err.println(JSON.toJSONString(dto));
        return "system/menu/change";
    }
    
    @RequestMapping(value = "change", method = RequestMethod.POST)
    @ResponseBody
    public String change(HttpServletRequest req) {
        String id=req.getParameter("id");
        AdminMenuDto dto = adminMenuService.findById(Long.parseLong(id));
        if (dto == null) {
            throw new RuntimeException("数据有误!");
        }
        String name = req.getParameter("userName");
        String menuUrl = req.getParameter("menuUrl");
        String status = req.getParameter("status");
        AdminMenuDto newDto= new AdminMenuDto();
        newDto.setId(dto.getId());
        newDto.setMenuName(name);
        newDto.setMenuUrl(menuUrl);
        newDto.setStatus(InvalidStatus.getEnum(Integer.parseInt(status)));
        adminMenuService.update(newDto);
        return ResponseJsonUtils.getSuccessMsg("修改成功！", "/system/menu/list");
    }

    @RequestMapping(value = "addMenu", method = RequestMethod.GET)
    public String addMenu(Model model, HttpServletRequest req) {
        String id = req.getParameter("id");
        AdminMenuDto dto = adminMenuService.findById(Long.parseLong(id));
        if (dto.getLevel() >= 4) {
            throw new RuntimeException("三级菜单不支持添加下级菜单!");
        }
        model.addAttribute("parentId", dto.getId());
        model.addAttribute("parentName", dto.getMenuName());
        return "system/menu/addMenu";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String addSave(Model model, HttpServletRequest req) {
        String name = req.getParameter("userName");
        String menuUrl = req.getParameter("menuUrl");
        String status = req.getParameter("status");
        String parentId = req.getParameter("parentId");
        AdminMenuDto dto = new AdminMenuDto();
        AdminMenuDto parentDto = null;
        if (StringUtils.isNotEmpty(parentId)) {
            parentDto = adminMenuService.findById(Long.parseLong(parentId));
        }
        if (parentDto != null) {
            if (parentDto.getLevel() == 4) {
                throw new RuntimeException("不支持添加子菜单！");
            }
            dto.setLevel(parentDto.getLevel() + 1);
            dto.setParentId(parentDto.getId());
        } else {
            dto.setLevel(1);
        }
        dto.setMenuName(name);
        dto.setMenuUrl(menuUrl);
        if (StringUtils.isNotEmpty(status)) {
            dto.setStatus(InvalidStatus.getEnum(Integer.parseInt(status)));
        } else {
            dto.setStatus(InvalidStatus.INVALID);
        }
        adminMenuService.save(dto);
        return ResponseJsonUtils.getSuccessMsg("添加成功！", "/system/menu/list");
    }
    
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Model model, HttpServletRequest req) {
        String id = req.getParameter("id");
        AdminMenuDto dto = adminMenuService.findById(Long.parseLong(id));
        if (dto==null) {
            throw new RuntimeException("菜单不存在！");
        }
        adminMenuService.deleteById(dto.getId());
        return ResponseJsonUtils.getSuccessMsg("操作成功", "/system/menu/list");
    }
    @RequestMapping(value = "invalid", method = RequestMethod.POST)
    @ResponseBody
    public String invalid(Model model, HttpServletRequest req) {
        String id = req.getParameter("id");
        AdminMenuDto dto = adminMenuService.findById(Long.parseLong(id));
        if (dto==null) {
            throw new RuntimeException("菜单不存在！");
        }
        dto.setStatus(InvalidStatus.INVALID);
        adminMenuService.update(dto);
        return ResponseJsonUtils.getSuccessMsg("操作成功", "/system/menu/list");
    }
    @RequestMapping(value = "effective", method = RequestMethod.POST)
    @ResponseBody
    public String effective(Model model, HttpServletRequest req) {
        String id = req.getParameter("id");
        AdminMenuDto dto = adminMenuService.findById(Long.parseLong(id));
        if (dto==null) {
            throw new RuntimeException("菜单不存在！");
        }
        dto.setStatus(InvalidStatus.EFFECTIVE);
        adminMenuService.update(dto);
        return ResponseJsonUtils.getSuccessMsg("操作成功", "/system/menu/list");
    }
}
