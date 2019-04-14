package com.sinolife.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinolife.dao.BusinessDao;
import com.sinolife.dao.PublishDao;
import com.sinolife.dao.RequirementDao;
import com.sinolife.model.Business;
import com.sinolife.model.EntityType;
import com.sinolife.model.Requirement;
import com.sinolife.model.RequirementDTO;
import com.sinolife.muti.thread.RequirementBusinessCallable;
import com.sinolife.muti.thread.RequirementStateCallable;
import com.sinolife.util.BusinessConst;
import com.sinolife.util.RedisKeyUtil;
import com.sinolife.util.StateConst;

@Service
public class RequirementService {
	private static final Logger logger = LoggerFactory.getLogger(RequirementService.class);

	@Autowired
	RedisService redisService;

	@Autowired
	RequirementDao requirementDao;

	@Autowired
	BusinessDao businessDao;

	@Autowired
	PublishDao publishDao;

	/**
	 * 获取排期内需求状态详情
	 * 
	 * @param publishId
	 * @return
	 */
	public Map<String, Object> getRequirementDetails(int publishId) {

		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			// 判断redis缓存是否存在数据
			String key = RedisKeyUtil.getPublishStateDetailsKey(EntityType.ENTITY_PUBLISH, publishId);
			if (redisService.get(key) != null) {
				msg = redisService.get(key);
				return msg;
			// 不存在缓存则多线程查询数据库
			} else {
				// 查询所有
				RequirementStateCallable allCallable = new RequirementStateCallable(requirementDao, businessDao,
						publishDao, publishId, 0);
				FutureTask<List<RequirementDTO>> allTask = new FutureTask<List<RequirementDTO>>(allCallable);

				new Thread(allTask).start();
				// 查询未分配
				RequirementStateCallable unkownCallable = new RequirementStateCallable(requirementDao, businessDao,
						publishDao, publishId, StateConst.UNKOWN);
				FutureTask<List<RequirementDTO>> unkownTask = new FutureTask<List<RequirementDTO>>(unkownCallable);

				new Thread(unkownTask).start();
				// 查询审批中
				RequirementStateCallable approvingCallable = new RequirementStateCallable(requirementDao, businessDao,
						publishDao, publishId, StateConst.APPROVING);
				FutureTask<List<RequirementDTO>> approvingTask = new FutureTask<List<RequirementDTO>>(
						approvingCallable);

				new Thread(approvingTask).start();
				// 查询开发中
				RequirementStateCallable developingCallable = new RequirementStateCallable(requirementDao, businessDao,
						publishDao, publishId, StateConst.DEVELOPING);
				FutureTask<List<RequirementDTO>> developingTask = new FutureTask<List<RequirementDTO>>(
						developingCallable);

				new Thread(developingTask).start();
				// 查询内测中
				RequirementStateCallable inTestingCallable = new RequirementStateCallable(requirementDao, businessDao,
						publishDao, publishId, StateConst.IN_TESTING);
				FutureTask<List<RequirementDTO>> inTestingTask = new FutureTask<List<RequirementDTO>>(
						inTestingCallable);

				new Thread(inTestingTask).start();
				// 查询已移交测试
				RequirementStateCallable outTestingCallable = new RequirementStateCallable(requirementDao, businessDao,
						publishDao, publishId, StateConst.OUT_TESTING);
				FutureTask<List<RequirementDTO>> outTestingTask = new FutureTask<List<RequirementDTO>>(
						outTestingCallable);

				new Thread(outTestingTask).start();
				// 查询完成
				RequirementStateCallable finishedCallable = new RequirementStateCallable(requirementDao, businessDao,
						publishDao, publishId, StateConst.FINISHED);
				FutureTask<List<RequirementDTO>> finishedTask = new FutureTask<List<RequirementDTO>>(finishedCallable);

				new Thread(finishedTask).start();

				msg.put("success", "获取成功");
				msg.put("all", allTask.get());
				msg.put("unkown", unkownTask.get());
				msg.put("approving", approvingTask.get());
				msg.put("developing", developingTask.get());
				msg.put("in_testing", inTestingTask.get());
				msg.put("out_testing", outTestingTask.get());
				msg.put("finished", finishedTask.get());
				redisService.set(key, msg);
				return msg;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg.put("error", "获取需求详情异常");
		}
		return msg;
	}

	public Map<String, Object> updateRequirement(String id, String jiraNo, String jiraDesc, String developer,
			String tester, String reporter, String businessDesc, String state, String manpower, String workDay,
			String updatedUser) {
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
			String key = RedisKeyUtil.getPublishStateDetailsKey(EntityType.ENTITY_PUBLISH, requirement.getPublishId());
			// 清除缓存
			redisService.remove(key);
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg.put("error", "更新出现异常");
			return msg;
		}
	}

	public Map<String, Object> getRequirementByBusiness(int publishId) {
		Map<String, Object> msg = new HashMap<String, Object>();
		String key = RedisKeyUtil.getPublishBusinessDetailsKey(EntityType.ENTITY_PUBLISH, publishId);
		try {
			if (redisService.get(key) != null) {
				msg = redisService.get(key);
				return msg;
			} else {
				// 查询寿险
				RequirementBusinessCallable sxRequirementCallable = new RequirementBusinessCallable(requirementDao,
						businessDao, publishDao, publishId, BusinessConst.SX);
				FutureTask<List<RequirementDTO>> sxRequirementTask = new FutureTask<>(sxRequirementCallable);
				new Thread(sxRequirementTask).start();

				// 查询产险
				RequirementBusinessCallable cxRequirementCallable = new RequirementBusinessCallable(requirementDao,
						businessDao, publishDao, publishId, BusinessConst.CX);
				FutureTask<List<RequirementDTO>> cxRequirementTask = new FutureTask<>(cxRequirementCallable);
				new Thread(cxRequirementTask).start();

				// 查询团险
				RequirementBusinessCallable txRequirementCallable = new RequirementBusinessCallable(requirementDao,
						businessDao, publishDao, publishId, BusinessConst.TX);
				FutureTask<List<RequirementDTO>> txRequirementTask = new FutureTask<>(txRequirementCallable);
				new Thread(txRequirementTask).start();

				// 查询财务
				RequirementBusinessCallable cwRequirementCallable = new RequirementBusinessCallable(requirementDao,
						businessDao, publishDao, publishId, BusinessConst.CW);
				FutureTask<List<RequirementDTO>> cwRequirementTask = new FutureTask<>(cwRequirementCallable);
				new Thread(cwRequirementTask).start();

				// 查询电商
				RequirementBusinessCallable dsRequirementCallable = new RequirementBusinessCallable(requirementDao,
						businessDao, publishDao, publishId, BusinessConst.DS);
				FutureTask<List<RequirementDTO>> dsRequirementTask = new FutureTask<>(dsRequirementCallable);
				new Thread(dsRequirementTask).start();

				// 查询规则
				RequirementBusinessCallable gzRequirementCallable = new RequirementBusinessCallable(requirementDao,
						businessDao, publishDao, publishId, BusinessConst.GZ);
				FutureTask<List<RequirementDTO>> gzRequirementTask = new FutureTask<>(gzRequirementCallable);
				new Thread(gzRequirementTask).start();

				// 查询监管
				RequirementBusinessCallable jgRequirementCallable = new RequirementBusinessCallable(requirementDao,
						businessDao, publishDao, publishId, BusinessConst.JG);
				FutureTask<List<RequirementDTO>> jgRequirementTask = new FutureTask<>(jgRequirementCallable);
				new Thread(jgRequirementTask).start();
				msg.put("sx", sxRequirementTask.get());
				msg.put("cx", cxRequirementTask.get());
				msg.put("tx", txRequirementTask.get());
				msg.put("cw", cwRequirementTask.get());
				msg.put("ds", dsRequirementTask.get());
				msg.put("gz", gzRequirementTask.get());
				msg.put("jg", jgRequirementTask.get());
				redisService.set(key, msg);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg.put("error", "获取异常");
		}
		return msg;
	}
}
