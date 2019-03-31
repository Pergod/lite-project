package com.sinolife.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinolife.service.RequirementService;
import com.sinolife.util.JSONUtil;

@Controller
public class RequirementController {

	@Autowired
	private RequirementService requirementService;

	/**
	 * 查询排期下的需求详情
	 * @param publishId
	 * @return
	 */
	@RequestMapping(path = { "/getRequirementDetails" }, method = { RequestMethod.GET })
	@ResponseBody
	public String getRequirementDetails(@RequestParam("publish_id") int publishId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = requirementService.getRequirementDetails(publishId);
			if (map.containsKey("success")) {
				return JSONUtil.getJSONString(0, map);
			} else {
				return JSONUtil.getJSONString(1, map);
			}
		} catch (Exception e) {
			return JSONUtil.getJSONString(1, "获取详情异常");
		}
	}
}
