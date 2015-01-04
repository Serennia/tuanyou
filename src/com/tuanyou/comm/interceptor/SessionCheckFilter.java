package com.tuanyou.comm.interceptor;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tuanyou.comm.utils.BaseUtils;
import com.tuanyou.comm.utils.Constant;
import com.tuanyou.comm.utils.PathMatcher;
import com.tuanyou.comm.utils.ReadProperties;


public class SessionCheckFilter implements Filter {
	
	
	private String passURL = new ReadProperties("config/Global.properties").getValue("passURL");
	private PathMatcher pathMatcher = new PathMatcher();

	public void destroy() {

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		if (this.passURL(request)) {
			filterChain.doFilter(request, response);
			return;
		}
	
		if (request.getSession().getAttribute(Constant.SYS_USER_INFO) == null) {
			
			response.sendRedirect(request.getContextPath()+"/url.jsp?param=pages/login");
			return;
		}

		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}
	
	
	/**
	 * 验证是否是不需要验证的URL
	 * 
	 * @param request
	 * @return
	 */
	public boolean passURL(HttpServletRequest request) {
		String rurl = request.getRequestURI();
		String passURLPattern = this.passURL;
		boolean isPass = false;
		if (!BaseUtils.isEmpty(passURLPattern) && !isPass) {
			String[] passURLs = passURLPattern.split(";");
			for (int i = 0; i < passURLs.length; i++) {
				isPass = pathMatcher.match(passURLs[i], rurl);
				if (isPass) {
					break;
				}
			}
		}
		return isPass;
	}

}
