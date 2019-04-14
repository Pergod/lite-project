package com.sinolife.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinolife.model.HostHolder;
import com.sinolife.model.RequirementDTO;
import com.sinolife.service.RequirementService;
import com.sinolife.util.JSONUtil;

@Controller
public class RequirementController {
	private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);

	@Autowired
	private RequirementService requirementService;

	@Autowired
	HostHolder hostHolder;

	/**
	 * 查询排期下的需求详情
	 * 
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
			logger.error(e.getMessage());
			return JSONUtil.getJSONString(1, "获取详情异常");
		}
	}

	/**
	 * 更新需求详情
	 * 
	 * @param publishId
	 * @return
	 */
	@RequestMapping(path = { "/updateRequirement" }, method = { RequestMethod.POST })
	@ResponseBody
	public String updateRequirement(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			String jiraNo = request.getParameter("jira_no");
			String jiraDesc = request.getParameter("jira_desc");
			String developer = request.getParameter("developer");
			String tester = request.getParameter("tester");
			String reporter = request.getParameter("reporter");
			String business = request.getParameter("business");
			String state = request.getParameter("state");
			String manpower = request.getParameter("manpower");
			String workDay = request.getParameter("workDay");
			String updatedUser = hostHolder.getUser().getUserName();
			map = requirementService.updateRequirement(id, jiraNo, jiraDesc, developer, tester, reporter, business,
					state, manpower, workDay, updatedUser);
			if (map.containsKey("success")) {
				return JSONUtil.getJSONString(0, map);
			} else {
				return JSONUtil.getJSONString(1, map);
			}
		} catch (Exception e) {
			return JSONUtil.getJSONString(1, "获取详情异常");
		}
	}

	/**
	 * 获取条线需求
	 * 
	 * @param publishId
	 * @return
	 */
	@RequestMapping(path = { "/getBusinessRequirements" }, method = { RequestMethod.GET })
	@ResponseBody
	public String getBusinessRequirements(@RequestParam("publish_id") int publishId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = requirementService.getRequirementByBusiness(publishId);
			Map<String, Object> results = new HashMap<String, Object>();
			results = transferToPageData(map);
			if (results.containsKey("success")) {
				return JSONUtil.getJSONString(0, results);
			} else {
				return JSONUtil.getJSONString(1, results);
			}
		} catch (Exception e) {
			return JSONUtil.getJSONString(1, "获取详情异常");
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String,Object> transferToPageData(Map<String, Object> beforeMap) {
		Set<String> keys = beforeMap.keySet();
		List<Object> results = new ArrayList<Object>();
		Map<String, Object> afterMap = new HashMap<String, Object>();
		for (String key : keys) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Object> list = new ArrayList<Object>();
			List<RequirementDTO> requirements = (List<RequirementDTO>) beforeMap.get(key);
			for (RequirementDTO item : requirements) {
				Map<String, Object> childrenMap = new HashMap<String, Object>();
				childrenMap.put("text", item.getRequirement().getJiraNo() + " " +item.getRequirement().getJiraDesc());
				childrenMap.put("id", item.getRequirement().getId());
				list.add(childrenMap);
			}
			map.put("children", list);
			if (key.equalsIgnoreCase("sx")) {
				map.put("text", "寿险" + "(" + requirements.size() + ")");
			} else if (key.equalsIgnoreCase("cx")) {
				map.put("text", "产险" + "(" + requirements.size() + ")");
			} else if (key.equalsIgnoreCase("tx")) {
				map.put("text", "团险" + "(" + requirements.size() + ")");
			} else if (key.equalsIgnoreCase("cw")) {
				map.put("text", "财务" + "(" + requirements.size() + ")");
			} else if (key.equalsIgnoreCase("ds")) {
				map.put("text", "电商" + "(" + requirements.size() + ")");
			} else if (key.equalsIgnoreCase("gz")) {
				map.put("text", "规则" + "(" + requirements.size() + ")");
			} else if (key.equalsIgnoreCase("jg")) {
				map.put("text", "监管" + "(" + requirements.size() + ")");
			}
			results.add(map);
		}
		afterMap.put("results", results);
		afterMap.put("success", "获取成功");
		return afterMap;
	}
}
