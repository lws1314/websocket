package com.lws.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lws.model.DevUser;
import com.lws.util.Constants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class SysInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		DevUser devUser=(DevUser)request.getSession().getAttribute(Constants.DEVUSER_SESSION);
		if(devUser==null){
			response.sendRedirect(request.getContextPath()+"/404.jsp");
			return false;
		}
		return true;
	}

	
	
}
