package com.sinolife.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 主页，查询排期信息
 * @author Flystar
 *
 */
@Controller
public class IndexController {
	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	
	
	/**
	 * 注册
	 * @return
	 */
    @ResponseBody
    public String register() {
		
        return "";
    }
	
	
	/**
	 * 登录
	 * @return
	 */
    @ResponseBody
    public String login() {
		
        return "";
    }
    
    
	
}	
