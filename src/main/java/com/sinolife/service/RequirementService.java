package com.sinolife.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinolife.dao.RequirementDao;
import com.sinolife.model.Requirement;
import com.sinolife.util.StateConst;

@Service
public class RequirementService {
	private static final Logger logger = Logger.getLogger(RequirementService.class);

	@Autowired
	RequirementDao requirementDao;

	public Map<String, Object> getRequirementDetails(int publishId) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			List<Requirement> all = requirementDao.selectAllRequirement(publishId);
			List<Requirement> unkown = requirementDao.selectRequirementStateCount(publishId, StateConst.UNKOWN);
			List<Requirement> approving = requirementDao.selectRequirementStateCount(publishId, StateConst.APPROVING);
			List<Requirement> developing = requirementDao.selectRequirementStateCount(publishId, StateConst.DEVELOPING);
			List<Requirement> in_testing = requirementDao.selectRequirementStateCount(publishId, StateConst.IN_TESTING);
			List<Requirement> out_testing = requirementDao.selectRequirementStateCount(publishId, StateConst.OUT_TESTING);
			List<Requirement> finished = requirementDao.selectRequirementStateCount(publishId, StateConst.FINISHED);
			msg.put("success", "获取成功");
			msg.put("all", all);
			msg.put("unkown", unkown);
			msg.put("approving", approving);
			msg.put("developing", developing);
			msg.put("in_testing", in_testing);
			msg.put("out_testing", out_testing);
			msg.put("finished", finished);
		} catch (Exception e) {
			logger.error(e);
			msg.put("error", "获取需求详情异常");
		}
		return msg;
	}
}
