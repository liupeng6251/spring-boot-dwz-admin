package org.qvit.hybrid.sys.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.qvit.hybrid.base.vo.PageVO;
import org.qvit.hybrid.enums.InvalidStatus;
import org.qvit.hybrid.sys.dto.AdminRoleDto;
import org.qvit.hybrid.sys.dto.AdminUserDto;
import org.qvit.hybrid.sys.service.AdminRoleService;
import org.qvit.hybrid.sys.service.AdminUserService;
import org.qvit.hybrid.utils.DateUtils;
import org.qvit.hybrid.utils.EncryptUtils;
import org.qvit.hybrid.utils.ResponseJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;

@Controller
@RequestMapping("/system/user")
public class AminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Value("${encrypt.key}")
    private String encryptKey;

    @RequestMapping(value = "page", method = { RequestMethod.GET, RequestMethod.POST })
   @ResponseBody
    public Object page( Model model, HttpServletRequest req, @RequestBody String body) {
        System.err.println(req.getQueryString());
        System.err.println(body);
      JSONObject map= JSON.parseObject(body);
        Integer  pageNo= MapUtils.getInteger(map,"pageNo");
        Integer pageSize=MapUtils.getInteger(map,"pageSize");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        AdminUserDto dto = new AdminUserDto();
        if (StringUtils.isNotEmpty(email)) {
            dto.setEmail(email);
        }
        if (StringUtils.isNotEmpty(mobile)) {
            dto.setMobile(mobile);
        }
        Page<AdminUserDto> value = adminUserService.findList(pageNo,pageSize, dto);
        PageVO<AdminUserDto> pageVo= new PageVO<>();
        pageVo.setResults(value);
        pageVo.setPageNo(value.getPageNum());
        pageVo.setTotalCount(value.getTotal());
        pageVo.setPageSize(value.getPageSize());
        return pageVo;
    }

    @RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
    public String list(Integer  pageNo,Integer pageSize, Model model, HttpServletRequest req) {
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        AdminUserDto dto = new AdminUserDto();
        if (StringUtils.isNotEmpty(email)) {
            dto.setEmail(email);
        }
        if (StringUtils.isNotEmpty(mobile)) {
            dto.setMobile(mobile);
        }
        Page<AdminUserDto> value = adminUserService.findList(pageNo,pageSize, dto);
        PageVO<AdminUserDto> pageVo= new PageVO<>();
        pageVo.setResults(value);
        pageVo.setPageNo(value.getPageNum());
        pageVo.setTotalCount(value.getTotal());
        pageVo.setPageSize(value.getPageSize());
        model.addAttribute("page", pageVo);
        model.addAttribute("email", email);
        model.addAttribute("mobile", mobile);
        return "system/user/list";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String add(Model model, HttpServletRequest req) {
        String userName = req.getParameter("userName");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        String occupation = req.getParameter("occupation");
        String department = req.getParameter("department");
        String jobNumber = req.getParameter("jobNumber");
        String entryDate = req.getParameter("entryDate");
        String status = req.getParameter("status");
        String password = req.getParameter("password");
        AdminUserDto dto = new AdminUserDto();
        dto.setUserName(userName);
        dto.setEmail(email);
        dto.setMobile(mobile);
        dto.setOccupation(occupation);
        dto.setDepartment(department);
        dto.setJobNumber(jobNumber);
        if (StringUtils.isNotEmpty(entryDate)) {
            dto.setEntryDate(DateUtils.parseDate(entryDate, "yyyy-MM-dd"));
        }
        if (StringUtils.isNotEmpty(status)) {
            dto.setStatus(InvalidStatus.getEnum(Integer.parseInt(status)));
        } else {
            dto.setStatus(InvalidStatus.INVALID);
        }
        dto.setPassword(EncryptUtils.md5(EncryptUtils.md5(password)));
        adminUserService.save(dto);
        return ResponseJsonUtils.getSuccessMsg("保存成功！", "/system/user/list", "closeCurrent", null, null);

    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addSave() {
        return "system/user/add";
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.GET)
    public String changePassword(Model model, HttpServletRequest req) {
        String id = req.getParameter("id");
        AdminUserDto dto = adminUserService.findById(Long.parseLong(id));
        if (dto == null) {
            throw new RuntimeException("草错有无，请刷新后再试！");
        }
        model.addAttribute("user", dto);
        model.addAttribute("id", EncryptUtils.encrypt(encryptKey, String.valueOf(dto.getId())));
        return "system/user/changePassword";
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    @ResponseBody
    public String changePasswordSave(Model model, HttpServletRequest req) {
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(password)) {
            return ResponseJsonUtils.getUnknowErrorJson("操作有误！");
        }
        adminUserService.updatePassword(Long.parseLong(EncryptUtils.decrypt(encryptKey, id)), EncryptUtils.md5(EncryptUtils.md5(password)));
        return ResponseJsonUtils.getSuccessMsg("修改成功！", "/system/user/list", "closeCurrent", null, null);
    }

    @RequestMapping(value = "change", method = RequestMethod.GET)
    public String change(Model model, HttpServletRequest req) {
        String id = req.getParameter("id");
        AdminUserDto dto = adminUserService.findById(Long.parseLong(id));
        if (dto == null) {
            throw new RuntimeException("草错有无，请刷新后再试！");
        }
        model.addAttribute("user", dto);
        model.addAttribute("id", EncryptUtils.encrypt(encryptKey, String.valueOf(dto.getId())));
        return "system/user/change";
    }

    @RequestMapping(value = "change", method = RequestMethod.POST)
    @ResponseBody
    public String changeSave(Model model, HttpServletRequest req) {
        String userName = req.getParameter("userName");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        String occupation = req.getParameter("occupation");
        String department = req.getParameter("department");
        String jobNumber = req.getParameter("jobNumber");
        String entryDate = req.getParameter("entryDate");
        String status = req.getParameter("status");
        String id = req.getParameter("id");
        AdminUserDto dto = new AdminUserDto();
        dto.setUserName(userName);
        dto.setEmail(email);
        dto.setMobile(mobile);
        dto.setOccupation(occupation);
        dto.setDepartment(department);
        dto.setJobNumber(jobNumber);
        dto.setId(Long.parseLong(EncryptUtils.decrypt(encryptKey, id)));
        if (StringUtils.isNotEmpty(entryDate)) {
            dto.setEntryDate(DateUtils.parseDate(entryDate, "yyyy-MM-dd"));
        }
        if (StringUtils.isNotEmpty(status)) {
            dto.setStatus(InvalidStatus.getEnum(Integer.parseInt(status)));
        } else {
            dto.setStatus(InvalidStatus.INVALID);
        }
        adminUserService.updateInfo(dto);
        return ResponseJsonUtils.getSuccessMsg("保存成功！", "/system/user/list", "closeCurrent", null, null);
    }

    @RequestMapping(value = "effective", method = RequestMethod.POST)
    @ResponseBody
    public String effective(Model model, HttpServletRequest req) {
        String id = req.getParameter("id");
        AdminUserDto dto = new AdminUserDto();
        dto.setId(Long.parseLong(id));
        dto.setStatus(InvalidStatus.EFFECTIVE);
        adminUserService.updateInfo(dto);
        return ResponseJsonUtils.getSuccessMsg("保存成功！", "/system/user/list", "closeCurrent", null, null);
    }

    @RequestMapping(value = "invalid", method = RequestMethod.POST)
    @ResponseBody
    public String invalid(Model model, HttpServletRequest req) {
        String id = req.getParameter("id");
        AdminUserDto dto = new AdminUserDto();
        dto.setId(Long.parseLong(id));
        dto.setStatus(InvalidStatus.INVALID);
        adminUserService.updateInfo(dto);
        return ResponseJsonUtils.getSuccessMsg("保存成功！", "/system/user/list", "closeCurrent", null, null);
    }

    @RequestMapping(value = "role", method = RequestMethod.GET)
    public String role(Model model, HttpServletRequest req) {
        String id = req.getParameter("id");
        List<AdminRoleDto> allList = adminRoleService.findAll();
        List<AdminRoleDto> selectList = adminRoleService.findListByUserId(Long.parseLong(id));
        model.addAttribute("noSelectList", filter(selectList, allList));
        model.addAttribute("selectList", selectList);
        model.addAttribute("userId", EncryptUtils.encrypt(encryptKey, String.valueOf(id)));
        return "system/user/role";
    }

    @RequestMapping(value = "role", method = RequestMethod.POST)
    @ResponseBody
    public String roleSave(Model model, HttpServletRequest req) {
        String userId = req.getParameter("userId");
        String roleIds = req.getParameter("roleIds");
        if (StringUtils.isEmpty(userId)) {
            return ResponseJsonUtils.getUnknowErrorJson("参数不能为空！");
        }
        List<String> roleIdList = null;
        if (StringUtils.isNotEmpty(roleIds)) {
            roleIdList = Arrays.asList(roleIds.split(","));
        }
        adminUserService.changeRoleById(Long.parseLong(EncryptUtils.decrypt(encryptKey, userId)), roleIdList);
        return ResponseJsonUtils.getSuccessMsg("修改成功！", "/system/user/role", "closeCurrent", null, null);
    }

    private List<AdminRoleDto> filter(List<AdminRoleDto> selectList, List<AdminRoleDto> allList) {
        List<AdminRoleDto> retValue = new ArrayList<>();
        Map<Long, AdminRoleDto> map = new HashMap<>();
        if (CollectionUtils.isEmpty(selectList)) {
            return allList;
        }
        for (AdminRoleDto dto : selectList) {
            map.put(dto.getId(), null);
        }
        for (AdminRoleDto role : allList) {
            if (!map.containsKey(role.getId())) {
                retValue.add(role);
            }
        }
        return retValue;
    }
}
