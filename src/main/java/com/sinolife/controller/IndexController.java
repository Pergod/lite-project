package com.sinolife.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinolife.service.UserService;
import com.sinolife.util.JSONUtil;

@Controller
public class IndexController {
	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 登录
	 * @return
	 */
    @ResponseBody
    @RequestMapping(path = { "/login" }, method = {RequestMethod.POST })
    public String login(HttpServletRequest request,HttpServletResponse response) {
    	//根据微信昵称直接登录
    	String wxNickName = request.getParameter("wxNickName");
		if (StringUtils.isNotBlank(wxNickName)) {
			userService.loginByWx(wxNickName);
		}else{
			String userName = request.getParameter("userName");
	    	String passWord = request.getParameter("passWord");
	    	
		}
        return "";
    }
	
	/**
	 * 注册
	 * @return
	 */
    @ResponseBody
    @RequestMapping(path = { "/register" }, method = {RequestMethod.POST })
    public String register(
    		@RequestParam ("userName") String userName,
    		@RequestParam ("passWord") String passWord,
    		@RequestParam ("comfirmPwd") String comfirmPwd,
    		@RequestParam ("workarea") String workarea,
    		@RequestParam ("role") String role) {
    	Map<String, Object> map = userService.register(userName,passWord,comfirmPwd,workarea,role);
    	if (map.containsKey("success")) {
			return JSONUtil.getJSONString(0, "注册成功");
		}else{
			return JSONUtil.getJSONString(1, map);
		}
    }
}	
