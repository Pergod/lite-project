package com.sinolife.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinolife.service.UserService;
import com.sinolife.util.JSONUtil;
/**
 * 用户相关操作Controller
 * @author Flystar
 *
 */
@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 登录
	 * @return
	 */
    @ResponseBody
    @RequestMapping(path = { "/login" }, method = {RequestMethod.POST })
    public String login(HttpServletRequest request,HttpServletResponse response) {
    	Map<String, Object> map = new HashMap<String,Object>();
    	//根据微信昵称直接登录
    	String wxNickName = request.getParameter("wxNickName");
		if (StringUtils.isNotBlank(wxNickName)) {
			map = userService.loginByWx(wxNickName);
		//用户名，密码登录
		}else{
			String userName = request.getParameter("userName");
	    	String passWord = request.getParameter("passWord");
	    	map = userService.login(userName, passWord);
		}
		if (map.containsKey("success")) {
			logger.info(JSONUtil.getJSONString(0, map));
			return JSONUtil.getJSONString(0, map);
		}else{
			return JSONUtil.getJSONString(1, map);
		}
    }
    
    /**
	 * 登录
	 * @return
	 */
    @ResponseBody
    @RequestMapping(path = { "/bind" }, method = {RequestMethod.POST })
    public String bind(@RequestParam ("wxNickName") String wxNickName,
    					@RequestParam ("userName") String userName) {
    	Map<String, Object> map = new HashMap<String,Object>();
    	try {
    		map = userService.bind(wxNickName, userName);
			if (map.containsKey("success")) {
				return JSONUtil.getJSONString(0, map);
			}else{
				return JSONUtil.getJSONString(1, map);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return JSONUtil.getJSONString(1, "绑定异常");
		}
    }
	
	/**
	 * 注册
	 * @return
	 */
    @ResponseBody
    @RequestMapping(path = { "/register" }, method = {RequestMethod.POST })
    public String register(
    		@RequestParam ("userName") String userName,
    		@RequestParam ("passWord") String password,
    		@RequestParam ("comfirmPwd") String comfirmPwd,
    		@RequestParam ("email") String email,
    		@RequestParam ("gender") String gender,
    		@RequestParam ("workarea") String workarea,
    		@RequestParam ("role") String role) {
    	Map<String, Object> map = userService.register(userName,password,comfirmPwd,email,gender,workarea,role);
    	try {
			if (map.containsKey("success")) {
				return JSONUtil.getJSONString(0, map);
			}else{
				return JSONUtil.getJSONString(1, map);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return JSONUtil.getJSONString(1, "系统异常");
		}
    }
}	
