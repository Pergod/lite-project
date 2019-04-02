package com.sinolife.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinolife.dao.BusinessDao;
import com.sinolife.dao.PublishDao;
import com.sinolife.dao.RequirementDao;
import com.sinolife.model.Business;
import com.sinolife.model.Publish;
import com.sinolife.model.Requirement;
import com.sinolife.model.RequirementDTO;
import com.sinolife.util.StateConst;

@Service
public class RequirementService {
	private static final Logger logger = Logger.getLogger(RequirementService.class);

	@Autowired
	RequirementDao requirementDao;
	
	@Autowired
	BusinessDao businessDao;
	
	@Autowired
	PublishDao publishDao;

	public Map<String, Object> getRequirementDetails(int publishId) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			List<Requirement> allRequirements = requirementDao.selectAllRequirement(publishId);
			List<RequirementDTO> all = new ArrayList<RequirementDTO>();
			for (Requirement item : allRequirements) {
				Business business = businessDao.selectBusinessById(item.getBusinessId());
				Publish publish = publishDao.selectPublishById(item.getPublishId());
				RequirementDTO requirementDTO = new RequirementDTO();
				requirementDTO.setBusiness(business);
				requirementDTO.setPublish(publish);
				requirementDTO.setRequirement(item);
				all.add(requirementDTO);
			}
			List<Requirement> unkownRequirements = requirementDao.selectRequirementStateCount(publishId, StateConst.UNKOWN);
			List<RequirementDTO> unkown = new ArrayList<RequirementDTO>();
			for (Requirement item : unkownRequirements) {
				Business business = businessDao.selectBusinessById(item.getBusinessId());
				Publish publish = publishDao.selectPublishById(item.getPublishId());
				RequirementDTO requirementDTO = new RequirementDTO();
				requirementDTO.setBusiness(business);
				requirementDTO.setPublish(publish);
				requirementDTO.setRequirement(item);
				unkown.add(requirementDTO);
			}
			List<Requirement> approvingRequirements = requirementDao.selectRequirementStateCount(publishId, StateConst.APPROVING);
			List<RequirementDTO> approving = new ArrayList<RequirementDTO>();
			for (Requirement item : approvingRequirements) {
				Business business = businessDao.selectBusinessById(item.getBusinessId());
				Publish publish = publishDao.selectPublishById(item.getPublishId());
				RequirementDTO requirementDTO = new RequirementDTO();
				requirementDTO.setBusiness(business);
				requirementDTO.setPublish(publish);
				requirementDTO.setRequirement(item);
				approving.add(requirementDTO);
			}
			List<Requirement> developingRequirements = requirementDao.selectRequirementStateCount(publishId, StateConst.DEVELOPING);
			List<RequirementDTO> developing = new ArrayList<RequirementDTO>();
			for (Requirement item : developingRequirements) {
				Business business = businessDao.selectBusinessById(item.getBusinessId());
				Publish publish = publishDao.selectPublishById(item.getPublishId());
				RequirementDTO requirementDTO = new RequirementDTO();
				requirementDTO.setBusiness(business);
				requirementDTO.setPublish(publish);
				requirementDTO.setRequirement(item);
				developing.add(requirementDTO);
			}
			
			List<Requirement> inTestingRequirements = requirementDao.selectRequirementStateCount(publishId, StateConst.IN_TESTING);
			List<RequirementDTO> in_testing = new ArrayList<RequirementDTO>();
			for (Requirement item : inTestingRequirements) {
				Business business = businessDao.selectBusinessById(item.getBusinessId());
				Publish publish = publishDao.selectPublishById(item.getPublishId());
				RequirementDTO requirementDTO = new RequirementDTO();
				requirementDTO.setBusiness(business);
				requirementDTO.setPublish(publish);
				requirementDTO.setRequirement(item);
				in_testing.add(requirementDTO);
			}
			List<Requirement> outTestingRequirements = requirementDao.selectRequirementStateCount(publishId, StateConst.OUT_TESTING);
			List<RequirementDTO> out_testing = new ArrayList<RequirementDTO>();
			for (Requirement item : outTestingRequirements) {
				Business business = businessDao.selectBusinessById(item.getBusinessId());
				Publish publish = publishDao.selectPublishById(item.getPublishId());
				RequirementDTO requirementDTO = new RequirementDTO();
				requirementDTO.setBusiness(business);
				requirementDTO.setPublish(publish);
				requirementDTO.setRequirement(item);
				out_testing.add(requirementDTO);
			}
			List<Requirement> finishedRequirements = requirementDao.selectRequirementStateCount(publishId, StateConst.FINISHED);
			List<RequirementDTO> finished = new ArrayList<RequirementDTO>();
			for (Requirement item : finishedRequirements) {
				Business business = businessDao.selectBusinessById(item.getBusinessId());
				Publish publish = publishDao.selectPublishById(item.getPublishId());
				RequirementDTO requirementDTO = new RequirementDTO();
				requirementDTO.setBusiness(business);
				requirementDTO.setPublish(publish);
				requirementDTO.setRequirement(item);
				finished.add(requirementDTO);
			}
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

	public Map<String, Object> updateRequirement(
			String id, String jiraNo, String jiraDesc, String developer,
			String tester, String reporter, String businessDesc, String state,String manpower,String workDay,String updatedUser) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			int requirementId = Integer.valueOf(id);
			Business business = businessDao.selectBusinessByDesc(businessDesc);
			if (business == null) {
				msg.put("error", "条线不存在");
				return msg;
			}
			Requirement requirement = new Requirement();
			requirement.setId(requirementId);
			requirement.setJiraNo(jiraNo);
			requirement.setJiraDesc(jiraDesc);
			requirement.setDeveloper(developer);
			requirement.setTester(tester);
			requirement.setReporter(reporter);
			requirement.setBusinessId(business.getId());
			requirement.setManpower(Integer.valueOf(manpower));
			requirement.setWorkDay(Integer.valueOf(workDay));
			requirement.setUpdatedDate(new Date());
			requirement.setUpdatedUser(updatedUser);
			requirement.setState(Integer.valueOf(state));
			requirement.setUpdatedDate(new Date());
			requirementDao.updateRequirement(requirement);
			msg.put("success", "更新成功");
			return msg;
		} catch (Exception e) {
			logger.error(e);
			msg.put("error", "更新出现异常");
			return msg;
		}
	}
}
