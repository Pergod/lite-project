package com.sinolife.muti.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.sinolife.dao.BusinessDao;
import com.sinolife.dao.PublishDao;
import com.sinolife.dao.RequirementDao;
import com.sinolife.model.Business;
import com.sinolife.model.Publish;
import com.sinolife.model.Requirement;
import com.sinolife.model.RequirementDTO;

/**
 * 根据状态查询需求callable实现
 * @author Flystar
 *
 */
public class RequirementStateCallable implements Callable<List<RequirementDTO>> {
	
	private RequirementDao requirementDao;
	private BusinessDao businessDao;
	private PublishDao publishDao;
	private int publishId;
	private int state;

	public RequirementStateCallable(RequirementDao requirementDao,BusinessDao businessDao,PublishDao publishDao,int publishId, int state) {
		this.businessDao = businessDao;
		this.publishDao = publishDao;
		this.requirementDao = requirementDao;
		this.publishId = publishId;
		this.state = state;
	}

	@Override
	public List<RequirementDTO> call() throws Exception {
		List<Requirement> requirements = null;
		if (state != 0) {
			requirements = requirementDao.selectRequirementState(publishId, state);
		} else {
			requirements = requirementDao.selectAllRequirement(publishId);
		}
		List<RequirementDTO> results = new ArrayList<RequirementDTO>();
		for (Requirement item : requirements) {
			Business business = businessDao.selectBusinessById(item.getBusinessId());
			Publish publish = publishDao.selectPublishById(item.getPublishId());
			RequirementDTO requirementDTO = new RequirementDTO();
			requirementDTO.setBusiness(business);
			requirementDTO.setPublish(publish);
			requirementDTO.setRequirement(item);
			results.add(requirementDTO);
		}
		return results;
	}

}