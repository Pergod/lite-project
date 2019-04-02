package com.sinolife.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinolife.util.JSONUtil;

@Controller
public class IndexController {
	private static final Logger logger = Logger.getLogger(IndexController.class);

	@RequestMapping(path = { "/" }, method = { RequestMethod.GET })
	@ResponseBody
	public String index(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("success", "welcome!");
			return JSONUtil.getJSONString(0, map);
		} catch (Exception e) {
			logger.error(e);
			return JSONUtil.getJSONString(1, "访问异常");
		}

	}

}
