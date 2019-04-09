package com.sinolife.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.sinolife.service.RedisService;
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
			// 缓存不存在,则查询数据库
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
}
