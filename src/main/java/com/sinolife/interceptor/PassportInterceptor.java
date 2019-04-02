package com.sinolife.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sinolife.model.HostHolder;
import com.sinolife.model.User;
import com.sinolife.service.UserService;



@Component
public class PassportInterceptor implements HandlerInterceptor{
	private static final Logger logger = LoggerFactory.getLogger(PassportInterceptor.class);
	
	@Autowired
	private HostHolder hostHolder;
	
	@Autowired
	UserService userService;
	
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object obj, Exception e)
			throws Exception {
		
	}
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object obj, ModelAndView mv)
			throws Exception {
		
	}
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object obj) throws Exception {
		String userId = req.getParameter("userId");
		Map<String, Object> map = userService.getUserById(userId);
		logger.info("userId=" + userId);
		if (map.containsKey("success")) {
			hostHolder.setUser((User)map.get("success"));
			return true;
		}
		return false;
	}
	


}
