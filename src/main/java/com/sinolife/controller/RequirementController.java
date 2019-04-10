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

	private Map<String,Object> transferToPageData(Map<String, Object> beforeMap) {
		Set<String> keys = beforeMap.keySet();
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> afterMap = new HashMap<String, Object>();
		for (String key : keys) {
			if (key.equalsIgnoreCase("sx")) {
				Map<String, Object> sxMap = new HashMap<String, Object>();
				List<Object> sxList = new ArrayList<Object>();
				List<RequirementDTO> sxRequirements = (List<RequirementDTO>) beforeMap.get(key);
				for (RequirementDTO item : sxRequirements) {
					Map<String, Object> childrenMap = new HashMap<String, Object>();
					childrenMap.put("text", item.getRequirement().getJiraNo() + " " +item.getRequirement().getJiraDesc());
					childrenMap.put("id", item.getRequirement().getId());
					sxList.add(childrenMap);
				}
				sxMap.put("text", "寿险" + "(" + sxRequirements.size() + ")");
				sxMap.put("children", sxList);
				list.add(sxMap);
			} else if (key.equalsIgnoreCase("cx")) {
				Map<String, Object> cxMap = new HashMap<String, Object>();
				List<Object> cxList = new ArrayList<Object>();
				List<RequirementDTO> cxRequirements = (List<RequirementDTO>) beforeMap.get(key);
				for (RequirementDTO item : cxRequirements) {
					Map<String, Object> childrenMap = new HashMap<String, Object>();
					childrenMap.put("text", item.getRequirement().getJiraNo()+ " " +item.getRequirement().getJiraDesc());
					childrenMap.put("id", item.getRequirement().getId());
					cxList.add(childrenMap);
				}
				cxMap.put("text", "产险" + "(" + cxRequirements.size() + ")");
				cxMap.put("children", cxList);
				list.add(cxMap);
			} else if (key.equalsIgnoreCase("tx")) {
				Map<String, Object> txMap = new HashMap<String, Object>();
				List<Object> txList = new ArrayList<Object>();
				List<RequirementDTO> txRequirements = (List<RequirementDTO>) beforeMap.get(key);
				for (RequirementDTO item : txRequirements) {
					Map<String, Object> childrenMap = new HashMap<String, Object>();
					childrenMap.put("text", item.getRequirement().getJiraNo());
					childrenMap.put("id", item.getRequirement().getId());
					txList.add(childrenMap);
				}
				txMap.put("text", "团险" + "(" + txRequirements.size() + ")");
				txMap.put("children", txList);
				list.add(txMap);
			} else if (key.equalsIgnoreCase("cw")) {
				Map<String, Object> cwMap = new HashMap<String, Object>();
				List<Object> cwList = new ArrayList<Object>();
				List<RequirementDTO> cwRequirements = (List<RequirementDTO>) beforeMap.get(key);
				for (RequirementDTO item : cwRequirements) {
					Map<String, Object> childrenMap = new HashMap<String, Object>();
					childrenMap.put("text", item.getRequirement().getJiraNo());
					childrenMap.put("id", item.getRequirement().getId());
					cwList.add(childrenMap);
				}
				cwMap.put("text", "财务" + "(" + cwRequirements.size() + ")");
				cwMap.put("children", cwList);
				list.add(cwMap);
			} else if (key.equalsIgnoreCase("ds")) {
				Map<String, Object> dsMap = new HashMap<String, Object>();
				List<Object> dsList = new ArrayList<Object>();
				List<RequirementDTO> dsRequirements = (List<RequirementDTO>) beforeMap.get(key);
				for (RequirementDTO item : dsRequirements) {
					Map<String, Object> childrenMap = new HashMap<String, Object>();
					childrenMap.put("text", item.getRequirement().getJiraNo());
					childrenMap.put("id", item.getRequirement().getId());
					dsList.add(childrenMap);
				}
				dsMap.put("text", "电商" + "(" + dsRequirements.size() + ")");
				dsMap.put("children", dsList);
				list.add(dsMap);
			} else if (key.equalsIgnoreCase("gz")) {
				Map<String, Object> gzMap = new HashMap<String, Object>();
				List<Object> gzList = new ArrayList<Object>();
				List<RequirementDTO> gzRequirements = (List<RequirementDTO>) beforeMap.get(key);
				for (RequirementDTO item : gzRequirements) {
					Map<String, Object> childrenMap = new HashMap<String, Object>();
					childrenMap.put("text", item.getRequirement().getJiraNo());
					childrenMap.put("id", item.getRequirement().getId());
					gzList.add(childrenMap);
				}
				gzMap.put("text", "规则" + "(" + gzRequirements.size() + ")");
				gzMap.put("children", gzList);
				list.add(gzMap);
			} else if (key.equalsIgnoreCase("jg")) {
				Map<String, Object> jgMap = new HashMap<String, Object>();
				List<Object> jgList = new ArrayList<Object>();
				List<RequirementDTO> jgRequirements = (List<RequirementDTO>) beforeMap.get(key);
				for (RequirementDTO item : jgRequirements) {
					Map<String, Object> childrenMap = new HashMap<String, Object>();
					childrenMap.put("text", item.getRequirement().getJiraNo());
					childrenMap.put("id", item.getRequirement().getId());
					jgList.add(childrenMap);
				}
				jgMap.put("text", "监管" + "(" + jgRequirements.size() + ")");
				jgMap.put("children", jgList);
				list.add(jgMap);
			}
		}
		afterMap.put("results", list);
		afterMap.put("success", "获取成功");
		return afterMap;
	}
}
