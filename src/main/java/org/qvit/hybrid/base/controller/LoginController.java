package org.qvit.hybrid.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.qvit.hybrid.sys.dto.AdminMenuDto;
import org.qvit.hybrid.sys.dto.AdminRoleDto;
import org.qvit.hybrid.sys.dto.AdminUserDto;
import org.qvit.hybrid.sys.service.AdminMenuService;
import org.qvit.hybrid.sys.service.AdminRoleService;
import org.qvit.hybrid.sys.service.AdminUserService;
import org.qvit.hybrid.utils.EncryptUtils;
import org.qvit.hybrid.utils.ResponseJsonUtils;
import org.qvit.hybrid.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping()
public class LoginController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private AdminMenuService adminMenuService;

    @RequestMapping(path = "login", method = { RequestMethod.GET })
    public String toLogin() {
        return "login";
    }

    @RequestMapping(path = "login_dialog", method = { RequestMethod.GET })
    public String loginDialogView() {
        return "loginDialog";
    }

    @RequestMapping(path = "loginDialog", method = { RequestMethod.POST })
    @ResponseBody
    public String loginDialog(String username, String password, Model model) {
        // 后期考虑校验规则
        if (StringUtils.isBlank(username) && StringUtils.isBlank(password)) {
            return ResponseJsonUtils.getUnknowErrorJson("用户名和密码不能为空");
        }
        AdminUserDto user = null;
        if (username.contains("@")) {
            user = adminUserService.findByIdEmail(username);
        } else {
            user = adminUserService.findByIdMobile(username);
        }
        if (user == null) {
            return ResponseJsonUtils.getUnknowErrorJson("用户名或者密码错误");

        }
        String md5 = EncryptUtils.md5(EncryptUtils.md5(password));
        if (!md5.equals(user.getPassword())) {
            return ResponseJsonUtils.getUnknowErrorJson("用户名或者密码错误");
        }
        saveLoginStatus(user);
        return ResponseJsonUtils.getSuccessMsg("登录成功！");
    }

    private void saveLoginStatus(AdminUserDto user) {
        String createToken = UserUtils.createToken();
        List<AdminRoleDto> roleDtos = adminRoleService.findListByUserId(user.getId());
        List<Long> roleIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(roleDtos)) {
            for (AdminRoleDto roleDto : roleDtos) {
                roleIds.add(roleDto.getId());
            }
        }
        List<Long> menuIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(roleIds)) {
            List<AdminMenuDto> menus = adminMenuService.findListByRole(roleIds);
            if (CollectionUtils.isNotEmpty(menus)) {
                for (AdminMenuDto dto : menus) {
                    menuIds.add(dto.getId());
                }

            }
        }
        UserUtils.writeTokenToCookie(createToken);
        UserUtils.storeLoginUser(user, createToken);
        UserUtils.storeUserPermissions(menuIds, createToken);
    }

    @RequestMapping(path = "login", method = { RequestMethod.POST })
    @ResponseBody
    public Map<String, String> login(String username, String password, Model model) {
        Map<String, String> message = new HashMap<>();
        message.put("status", "false");
        // 后期考虑校验规则
        if (StringUtils.isBlank(username) && StringUtils.isBlank(password)) {
            message.put("status", "false");
            message.put("message", "用户名或者密码不能为空");
            return message;
        }
        AdminUserDto user = null;
        if (username.contains("@")) {
            user = adminUserService.findByIdEmail(username);
        } else {
            user = adminUserService.findByIdMobile(username);
        }
        if (user == null) {
            message.put("status", "false");
            message.put("message", "用户名或者密码错误！");
            return message;
        }
        String md5 = EncryptUtils.md5(EncryptUtils.md5(password));
        if (!md5.equals(user.getPassword())) {
            message.put("status", "false");
            message.put("message", "用户名或者密码错误！");
            return message;
        }
        saveLoginStatus(user);
        message.put("status", "true");
        return message;
    }

    @RequestMapping("logout")
    public String logout() {
        UserUtils.deleteLoginUser();
        return "redirect:login";
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.GET)
    public String changePasswordView() {
        return "changePassword";
    }

    @RequestMapping(value = "changePassword", method = RequestMethod.POST)
    @ResponseBody
    public String changePassword(String password, String newPassword) {
        AdminUserDto userDto = UserUtils.getLoginUser();
        AdminUserDto user = adminUserService.findById(userDto.getId());
        String md5 = EncryptUtils.md5(EncryptUtils.md5(password));
        if (!user.getPassword().equals(md5)) {
            throw new RuntimeException("密码输入错误！");
        }
        String md5pass = EncryptUtils.md5(EncryptUtils.md5(newPassword));
        if (!md5pass.equals(md5)) {
            adminUserService.updatePassword(user.getId(), md5pass);
        }
        UserUtils.deleteLoginUser();
        return ResponseJsonUtils.getSuccessMsg("修改成功", null, "closeCurrent", null, "/index");

    }

    public static void main(String[] args) {
        System.err.println( EncryptUtils.md5(EncryptUtils.md5("123456")));
    }
}
