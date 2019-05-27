package org.qvit.hybrid.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.qvit.hybrid.sys.dto.AdminMenuDto;
import org.qvit.hybrid.sys.service.AdminMenuService;
import org.qvit.hybrid.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class MenuCheckInterceptor extends HandlerInterceptorAdapter {

    private static Logger log = LoggerFactory.getLogger(MenuCheckInterceptor.class);

	private static final Set<String> NOT_NEED_LOGIN_URLS = new HashSet<>();
	

	static {
		NOT_NEED_LOGIN_URLS.add("/login");
		NOT_NEED_LOGIN_URLS.add("/logout");
		NOT_NEED_LOGIN_URLS.add("/error");
		NOT_NEED_LOGIN_URLS.add("/login_dialog");
		NOT_NEED_LOGIN_URLS.add("/index");
		NOT_NEED_LOGIN_URLS.add("/index");
		NOT_NEED_LOGIN_URLS.add("/system/user/page");

	}

    private LoadingCache<String, Map<String,Long>> cache = CacheBuilder.newBuilder().maximumSize(2).expireAfterAccess(600L, TimeUnit.SECONDS).refreshAfterWrite(60L,  TimeUnit.SECONDS).build(new MenuChche());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
		    String url = request.getServletPath();
		    log.info("requestUrl:{},request method:{}",url,request.getMethod());
			// 判断是否需要登录
			boolean result = checkNeedLogin(url, (HandlerMethod) handler);
			if (!result) {
				return true;
			}
			Map<String, Long> map = cache.get("1");
			if(!map.containsKey(url)){
			    return true;
			}
			result =checkUserPermission(map.get(url));
			if (!result) {
				response.getWriter().print("无访问权限！");
				return false;
			}
		}
		return true;
	}

	private boolean checkUserPermission(Long menuId) {
	    List<Long> permissionCodes = UserUtils.getPermissionCode();
        return permissionCodes.contains(menuId);
    }

    private class MenuChche extends CacheLoader<String, Map<String,Long>>{
        @Override
        public Map<String, Long> load(String arg0) throws Exception {
            AdminMenuService service = SpringContextHolder.getBean(AdminMenuService.class);
            List<AdminMenuDto> dtos = service.findListByEffective();
            Map<String,Long> retValue = new HashMap<String,Long>();
            for(AdminMenuDto dto:dtos){
                retValue.put(dto.getMenuUrl(), dto.getId());
            }
            return retValue;
        }
	    
	}
	
	private boolean checkNeedLogin(String url, HandlerMethod handler) {
		if (NOT_NEED_LOGIN_URLS.contains(url)) {
			return false;
		}
		return true;
	}

}
