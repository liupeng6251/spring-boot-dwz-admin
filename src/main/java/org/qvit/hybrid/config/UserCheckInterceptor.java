package org.qvit.hybrid.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.qvit.hybrid.utils.ResponseJsonUtils;
import org.qvit.hybrid.utils.UserUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserCheckInterceptor extends HandlerInterceptorAdapter {

	private static final Set<String> NOT_NEED_LOGIN_URLS = new HashSet<>();
	static {
		NOT_NEED_LOGIN_URLS.add("/login");
		NOT_NEED_LOGIN_URLS.add("/logout");
		NOT_NEED_LOGIN_URLS.add("/error");
		NOT_NEED_LOGIN_URLS.add("/login_dialog");
		NOT_NEED_LOGIN_URLS.add("/loginDialog");
		NOT_NEED_LOGIN_URLS.add("/system/user/page");

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			// 判断是否需要登录
			boolean result = checkNeedLogin(request, (HandlerMethod) handler);
			if (!result) {
				return true;
			}
			// 验证用户是否已登录
			result = checkUserLogin(request);
			if (!result) {
				gotoLogin(request, response);
				return result;
			}
			if (!result) {
				response.setStatus(403);
				response.sendRedirect(request.getContextPath() + "/login");
				return false;
			}
		}
		return true;
	}

	private boolean checkNeedLogin(HttpServletRequest request, HandlerMethod handler) {
		String url = request.getServletPath();
		if (NOT_NEED_LOGIN_URLS.contains(url)) {
			return false;
		}
		return true;
	}

	private void gotoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String ajaxHeader = request.getHeader("x-requested-with");
		if (StringUtils.isBlank(ajaxHeader)) {
			response.sendRedirect(request.getContextPath() + "/login");
		} else {// ajax请求
			String json = ResponseJsonUtils.getNeedLoginJson();
			response.getWriter().print(json);
			response.getWriter().flush();
		}
	}

	private boolean checkUserLogin(HttpServletRequest request) {
		Long userId = UserUtils.getLoginUserId();
		return userId != null;
	}
}
