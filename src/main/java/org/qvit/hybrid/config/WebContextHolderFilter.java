package org.qvit.hybrid.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * 方便获取web请求上下文，request、response等
 * 
 * @author tianyale 2016年6月22日
 *
 */
@Component
public class WebContextHolderFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		WebContextHolder.setRequestAndResponse((HttpServletRequest) req, (HttpServletResponse) resp);
		try {
			chain.doFilter(req, resp);
		} finally {
			WebContextHolder.cleanRequestAndResponse();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
