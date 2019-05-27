package org.qvit.hybrid.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.qvit.hybrid.config.SpringContextHolder;
import org.qvit.hybrid.config.WebContextHolder;
import org.qvit.hybrid.sys.dto.AdminUserDto;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 管理用户登录相关信息，前期用户存在session中，不支持多个部署，后期考虑存储在kv数据库中
 * 
 * @author tianyale 2016年7月27日
 *
 */
public class UserUtils {

    private static final String USER_KEY_PREFIX = "qunar:login_user_";
    private static final String USER_PERMISSION_KEY_PREFIX = "qunar:login_user_permission_";
    private static final String REQUST_USER_KEY_PREFIX = "REQUST_LOGIN_SYSTEM_USER";
    private static final String TOKEN_NAME = "token";
    private static final int TOKEN_EXPIRY = 30 * 60;

    public static void storeLoginUser(AdminUserDto user, String token) {
        String id = token;
        String jsonUser = JSON.toJSONString(user);
        StringRedisTemplate template = SpringContextHolder.getBean(StringRedisTemplate.class);
        template.opsForValue().set(USER_KEY_PREFIX + id, jsonUser, TOKEN_EXPIRY, TimeUnit.SECONDS);
    }

    public static Long getLoginUserId() {
        AdminUserDto AdminUserDto = getLoginUser();
        if (AdminUserDto != null) {
            return AdminUserDto.getId();
        }
        return null;
    }

    public static AdminUserDto getLoginUser() {
        HttpServletRequest request = WebContextHolder.getRequest();
        if (request == null) {
            return null;
        }
        AdminUserDto user = (AdminUserDto) request.getAttribute(REQUST_USER_KEY_PREFIX);
        if (user != null) {
            return user;
        }
        String token = getLoginToken();
        if (StringUtils.isBlank(token)) {
            return null;
        }
        StringRedisTemplate template = SpringContextHolder.getBean(StringRedisTemplate.class);
        String json = template.opsForValue().get(USER_KEY_PREFIX + token);
        AdminUserDto AdminUserDto = JSONObject.parseObject(json, AdminUserDto.class);
        if (AdminUserDto != null) {
            request.setAttribute(REQUST_USER_KEY_PREFIX, AdminUserDto);
            template.expire(USER_KEY_PREFIX + token, TOKEN_EXPIRY, TimeUnit.SECONDS);
            writeTokenToCookie(token);
            return AdminUserDto;
        }
        return null;
    }

    public static String getLoginUsername() {
        AdminUserDto AdminUserDto = getLoginUser();
        if (AdminUserDto != null) {
            return AdminUserDto.getUserName();
        }
        return null;
    }

    public static String getLoginToken() {
        HttpServletRequest request = WebContextHolder.getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (TOKEN_NAME.equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void deleteLoginUser() {
        HttpServletRequest request = WebContextHolder.getRequest();
        String id = getLoginToken();
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        StringRedisTemplate template = SpringContextHolder.getBean(StringRedisTemplate.class);
        template.delete(USER_KEY_PREFIX + id);
        template.delete(USER_PERMISSION_KEY_PREFIX + id);
    }

    @SuppressWarnings("unchecked")
    public static List<Long> getPermissionCode() {
        HttpServletRequest request = WebContextHolder.getRequest();
        String id = getLoginToken();
        if (StringUtils.isBlank(id)) {
            return new ArrayList<>();
        }
        List<Long> permissionCodes = (List<Long>) request.getAttribute("sys_user_permission_code");
        if (permissionCodes != null) {
            return permissionCodes;
        }
        StringRedisTemplate template = SpringContextHolder.getBean(StringRedisTemplate.class);
        String jsonStr = template.opsForValue().get(USER_PERMISSION_KEY_PREFIX + id);
        if (StringUtils.isNotBlank(jsonStr)) {
            List<Long> codes = JSONArray.parseArray(jsonStr, Long.class);
            request.setAttribute("sys_user_permission_code", codes);
            template.expire(USER_PERMISSION_KEY_PREFIX + id, TOKEN_EXPIRY, TimeUnit.SECONDS);
            return codes;
        }
        return new ArrayList<>();
    }

    public static void storeUserPermissions(List<Long> permissionCodes, String token) {
        String id = token;
        StringRedisTemplate template = SpringContextHolder.getBean(StringRedisTemplate.class);
        template.opsForValue().set(USER_PERMISSION_KEY_PREFIX + id, JSON.toJSONString(permissionCodes), TOKEN_EXPIRY, TimeUnit.SECONDS);
    }

    public static String createToken() {
        return UUID.randomUUID().toString().replace("-", "") + "|" + System.currentTimeMillis();
    }

    public static String createVerifyCodeCookieVal() {
        return UUID.randomUUID().toString().replace("-", "") + "|" + System.currentTimeMillis();
    }

    public static void writeTokenToCookie(String token) {
        HttpServletResponse response = WebContextHolder.getResponse();
        Cookie cookie = new Cookie(TOKEN_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(TOKEN_EXPIRY);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void writeVerifyCodeToCookie(String cookieVal) {
        HttpServletResponse response = WebContextHolder.getResponse();
        Cookie cookie = new Cookie("verify_token", cookieVal);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(5 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getVerifyCodeToCookie() {
        HttpServletRequest request = WebContextHolder.getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie coo : cookies) {
                if (coo != null) {
                    if ("verify_token".equals(coo.getName())) {
                        Cookie delCo = new Cookie("verify_token", null);
                        delCo.setHttpOnly(true);
                        delCo.setMaxAge(0);
                        delCo.setPath("/");
                        WebContextHolder.getResponse().addCookie(delCo);
                        return coo.getValue();
                    }
                }
            }
        }
        return null;
    }

    public static void storeVerifyCode(String cookieVal, String code) {
        StringRedisTemplate template = SpringContextHolder.getBean(StringRedisTemplate.class);
        template.opsForValue().set("verify_token_" + cookieVal, code, 5 * 60, TimeUnit.SECONDS);
    }

    public static String getAndDelVerifyCode(String cookieVal) {
        StringRedisTemplate template = SpringContextHolder.getBean(StringRedisTemplate.class);
        String val = template.opsForValue().get("verify_token_" + cookieVal);
        template.delete("verify_token_" + cookieVal);
        return val;
    }
}
