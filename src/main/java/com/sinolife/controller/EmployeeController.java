package com.sinolife.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sinolife.model.EmployeeDetail;
import com.sinolife.service.EmployeeService;


@Controller
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(path = { "/employee/{employeeId}" }, method = {RequestMethod.GET })
	@ResponseBody
	public String employeeDetail(@PathVariable("employeeId") int employeeId) {
		EmployeeDetail employeeDetail = employeeService.getEmployeeDetail(employeeId);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("EmployeeDetail", employeeDetail);
		String json = JSON.toJSONString(map);
		return json;
	}
}
