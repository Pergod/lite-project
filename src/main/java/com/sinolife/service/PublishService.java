package com.sinolife.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sinolife.dao.AttributeDao;
import com.sinolife.dao.BusinessDao;
import com.sinolife.dao.ProgessDao;
import com.sinolife.dao.PublishDao;
import com.sinolife.dao.RequirementDao;
import com.sinolife.dao.StateDao;
import com.sinolife.model.Attribute;
import com.sinolife.model.Business;
import com.sinolife.model.HostHolder;
import com.sinolife.model.PageBean;
import com.sinolife.model.Progress;
import com.sinolife.model.Publish;
import com.sinolife.model.PublishDTO;
import com.sinolife.model.Requirement;
import com.sinolife.model.State;
import com.sinolife.util.ExcelResolve;
import com.sinolife.util.FileUtil;
import com.sinolife.util.StateConst;

@Service
public class PublishService {
	private static final Logger logger = Logger.getLogger(PublishService.class);

	@Autowired
	private PublishDao publishDao;
	
	@Autowired
	private HostHolder hostHolder;

	@Autowired
	private RequirementDao requirementDao;

	@Autowired
	private ProgessDao progessDao;

	@Autowired
	private StateDao stateDao;
	
	@Autowired 
	private AttributeDao attributeDao;
	
	@Autowired 
	private BusinessDao businessDao;

	public Map<String, Object> addPublish(Publish publish) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			publishDao.insertPublish(publish);
			msg.put("success", "插入数据成功");
		} catch (Exception e) {
			logger.error(e);
			msg.put("error", "插入排期失败");
		}
		return msg;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> getPublishOffset(int startIndex, int pageSize) {
		Map<String, Object> msg = new HashMap<String, Object>();
		PageBean pageBean = new PageBean();
		List<PublishDTO> results = new ArrayList<PublishDTO>();
		List<Publish> publishs = publishDao.selectPublishOffset((startIndex - 1) * pageSize, pageSize);
		for (Publish publish : publishs) {
			// 获取排期内所有需求状态
			int publishId = publish.getId();
			// 查看当前版本进度
			int progressId = publish.getProgress();
			int all = requirementDao.selectAllRequirement(publishId) == null ? 0
					: requirementDao.selectAllRequirement(publishId).size();
			int unkown = requirementDao.selectRequirementStateCount(publishId, StateConst.UNKOWN) == null ? 0
					: requirementDao.selectRequirementStateCount(publishId, StateConst.UNKOWN).size();
			int approving = requirementDao.selectRequirementStateCount(publishId, StateConst.APPROVING) == null ? 0
					: requirementDao.selectRequirementStateCount(publishId, StateConst.APPROVING).size();
			int developing = requirementDao.selectRequirementStateCount(publishId, StateConst.DEVELOPING) == null ? 0
					: requirementDao.selectRequirementStateCount(publishId, StateConst.DEVELOPING).size();
			int in_testing = requirementDao.selectRequirementStateCount(publishId, StateConst.IN_TESTING) == null ? 0
					: requirementDao.selectRequirementStateCount(publishId, StateConst.IN_TESTING).size();
			int out_testing = requirementDao.selectRequirementStateCount(publishId, StateConst.OUT_TESTING) == null ? 0
					: requirementDao.selectRequirementStateCount(publishId, StateConst.OUT_TESTING).size();
			int finished = requirementDao.selectRequirementStateCount(publishId, StateConst.FINISHED) == null ? 0
					: requirementDao.selectRequirementStateCount(publishId, StateConst.FINISHED).size();
			Progress progress = progessDao.selectProgressById(progressId);

			PublishDTO publishDTO = new PublishDTO();
			publishDTO.setPublish(publish);
			publishDTO.setProgress(progress);
			publishDTO.setAll(all);
			publishDTO.setUnkown(unkown);
			publishDTO.setApproving(approving);
			publishDTO.setDeveloping(developing);
			publishDTO.setIn_testing(in_testing);
			publishDTO.setOut_testing(out_testing);
			publishDTO.setFinished(finished);
			results.add(publishDTO);
		}
		pageBean.setList(results);
		pageBean.setCurrentpage(startIndex);
		pageBean.setPagesize(pageSize);
		int totalRecord = publishs.size();
		pageBean.setTotalrecord(totalRecord);
		if (publishs.size() == 0) {
			msg.put("error", "未找到任何数据");
			return msg;
		}
		msg.put("success", pageBean);
		return msg;

	}

	@Transactional
	public Map<String, Object> UpdatePublish(String id, String attribute, String publishDate, String manager,
			String verifier, String deployer, String progress, String updatedUser) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			Publish publish = new Publish();
			publish.setId(Integer.valueOf(id));
			publish.setAttribute(Integer.valueOf(attribute));
			publish.setPublishDate(publishDate);
			publish.setManager(manager);
			publish.setVerifier(verifier);
			publish.setDeployer(deployer);
			publish.setProgress(Integer.valueOf(progress));
			publish.setUpdatedUser(updatedUser);
			publish.setUpdatedDate(new Date());
			publishDao.updatePublish(publish);
			msg.put("success", "更新成功");
		} catch (Exception e) {
			logger.error(e);
			msg.put("succee", "更新失败");
		}
		return msg;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> saveFile(MultipartFile file, String fileName) throws Exception {
		String extName = getExtName(file.getOriginalFilename());
		Map<String, Object> msg = new HashMap<String, Object>();
		if (!FileUtil.isExcel(extName)) {
			msg.put("error", "上传的文件非excel格式");
			return msg;
		}
		Files.copy(file.getInputStream(), new File(FileUtil.EXCEL_DIR + fileName).toPath(),
				StandardCopyOption.REPLACE_EXISTING);
		
		//根据文件名解析出版本日期及属性
		String preFileName = fileName.substring(0, fileName.indexOf("."));
		String publishDate = preFileName.substring(0, preFileName.lastIndexOf("-"));
		Publish publish = publishDao.selectPublishByDate(publishDate);
		String attributeDesc = preFileName.substring(preFileName.lastIndexOf("-") + 1);
		Attribute attribute = attributeDao.selectAttributeByDesc(attributeDesc);
		//判断是否已存在排期，如果不存在排期则新增
		if (publish==null) {
			publish = new Publish();
			publish.setAttribute(attribute.getId());
			publish.setPublishDate(publishDate);
			publishDao.insertPublish(publish);
		}
		if (attribute == null) {
			msg.put("error", "版本属性错误");
			return msg;
		}
		// 文件解析入库
		// 解析出所有条线及条线下的所有需求
		List<Map<String, Object>> businesses = ExcelResolve.readExcel(new File(FileUtil.EXCEL_DIR + fileName));
		for (Map<String, Object> businessMap : businesses) {
			// 获取条线名
			String businessDesc = (String) businessMap.get("business");
			Business business = businessDao.selectBusinessByDesc(businessDesc);
			if (business == null ) {
				msg.put("error", "条线不存在，请检查文件");
				return msg;
			}
			//获取条线下所有需求
			List<Map<String, String>> requirements = (List<Map<String, String>>) businessMap.get(businessDesc);
			for (Map<String, String> requirementMap : requirements) {
				// 需求号
				String jiraNo = requirementMap.get("jira_no");
				// 主题
				String jiraDesc = requirementMap.get("jira_desc");
				// 需求类型
				String jiraType = requirementMap.get("jira_type");
				// 需求状态
				String stateDesc = requirementMap.get("state");
				State state = stateDao.selectStateByDesc(stateDesc);
				if (state == null) {
					msg.put("error", "需求状态填写错误");
					return msg;
				}
				// 修改点
				String modification = requirementMap.get("modification");
				// 开发人员
				String developer = requirementMap.get("developer");
				// 是否需要代码评审
				String need_review = requirementMap.get("need_review");
				// 需求提出人
				String reporter = requirementMap.get("reporter");
				// 测试人
				String tester = requirementMap.get("tester");
				Requirement requirement = new Requirement();
				requirement.setJiraNo(jiraNo);
				requirement.setJiraDesc(jiraDesc);
				requirement.setState(state.getId());
				requirement.setDeveloper(developer);
				requirement.setReporter(reporter);
				requirement.setTester(tester);
				requirement.setBusinessId(business.getId());
				requirement.setCreatedDate(new Date());
				requirement.setUpdatedDate(new Date());
				requirement.setPublishId(publish.getId());
				requirement.setCreatedUser(hostHolder.getUser().getUserName());
				requirement.setUpdatedUser(hostHolder.getUser().getUserName());
				if ("Y".equalsIgnoreCase(need_review)) {
					requirement.setNeedReview(true);
				} else {
					requirement.setNeedReview(false);
				}
				requirementDao.insertRequirement(requirement);
			}
		}
		logger.info(ExcelResolve.readExcel(new File(FileUtil.EXCEL_DIR + fileName)));
		msg.put("success", FileUtil.DOMAIN + "excel?name=" + fileName);
		return msg;
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param originFileName
	 * @return
	 */
	public String getExtName(String originFileName) {
		int dotPos = originFileName.lastIndexOf(".");
		if (dotPos < 0) {
			return null;
		}
		return originFileName.substring(dotPos + 1);
	}

//	public static void main(String[] args) {
//		String fileName = "2018-09-12-常规.xls";
//		fileName = fileName.substring(0, fileName.indexOf("."));
//		System.out.println(fileName);
//		fileName.substring(0, fileName.lastIndexOf("-"));
//		System.out.println(fileName.substring(0, fileName.lastIndexOf("-")));
//		fileName.substring(fileName.lastIndexOf("-"));
//		System.out.println(fileName.substring(fileName.lastIndexOf("-") + 1));
//	}
}
