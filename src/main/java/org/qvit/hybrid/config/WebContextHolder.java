package org.qvit.hybrid.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebContextHolder {

	private static final ThreadLocal<HttpServletRequest> REQ_LOCAL = new ThreadLocal<HttpServletRequest>();

	private static final ThreadLocal<HttpServletResponse> RES_LOCAL = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return REQ_LOCAL.get();
	}

	public static HttpServletResponse getResponse() {
		return RES_LOCAL.get();
	}

	public static void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
		REQ_LOCAL.set(request);
		RES_LOCAL.set(response);
	}

	public static void cleanRequestAndResponse() {
		REQ_LOCAL.remove();
		RES_LOCAL.remove();
	}

}
