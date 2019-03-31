package com.sinolife.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sinolife.dao.PublishDao;
import com.sinolife.dao.RequirementDao;
import com.sinolife.model.PageBean;
import com.sinolife.model.Publish;
import com.sinolife.model.PublishDTO;
import com.sinolife.model.Requirement;
import com.sinolife.util.FileUtil;
import com.sinolife.util.StateConst;

@Service
public class PublishService {
	private static final Logger logger = Logger.getLogger(PublishService.class);

	@Autowired
	private PublishDao publishDao;
	
	@Autowired
	private RequirementDao requirementDao;

	public Map<String, Object> addPublish(Publish publish) {
		Map<String, Object> msg = new HashMap<String,Object>();
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
		Map<String, Object> msg = new HashMap<String,Object>();
		PageBean pageBean = new PageBean();
		List<PublishDTO> results = new ArrayList<PublishDTO>();
		List<Publish> publishs = publishDao.selectPublishOffset((startIndex - 1) * pageSize, pageSize);
		for (Publish publish : publishs) {
			//获取排期内所有需求状态
			int publishId = publish.getId();
			int all = requirementDao.selectAllRequirement(publishId)==null?0:requirementDao.selectAllRequirement(publishId).size();
			int unkown = requirementDao.selectRequirementStateCount(publishId, StateConst.UNKOWN)==null?0:requirementDao.selectRequirementStateCount(publishId, StateConst.UNKOWN).size();
			int approving = requirementDao.selectRequirementStateCount(publishId, StateConst.APPROVING)==null?0:requirementDao.selectRequirementStateCount(publishId, StateConst.APPROVING).size();
			int developing = requirementDao.selectRequirementStateCount(publishId, StateConst.DEVELOPING)==null?0:requirementDao.selectRequirementStateCount(publishId, StateConst.DEVELOPING).size();
			int in_testing = requirementDao.selectRequirementStateCount(publishId, StateConst.IN_TESTING)==null?0:requirementDao.selectRequirementStateCount(publishId, StateConst.IN_TESTING).size();
			int out_testing = requirementDao.selectRequirementStateCount(publishId, StateConst.OUT_TESTING)==null?0:requirementDao.selectRequirementStateCount(publishId, StateConst.OUT_TESTING).size();
			int finished = requirementDao.selectRequirementStateCount(publishId, StateConst.FINISHED)==null?0: requirementDao.selectRequirementStateCount(publishId, StateConst.FINISHED).size();
			PublishDTO publishDTO = new PublishDTO();
			publishDTO.setPublish(publish);
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
		if (publishs.size()==0) {
			msg.put("error", "未找到任何数据");
			return msg;
		}
		msg.put("success", pageBean);
		return msg;
		
	}
	
	@Transactional
	public Map<String, Object> UpdatePublish(String id, String attribute, String publishDate, String manager, String verifier, String deployer, String state) {
		Map<String, Object> msg = new HashMap<String,Object>();
		try {
			Publish publish = new Publish();
			publish.setId(Integer.valueOf(id));
			publish.setAttribute(Integer.valueOf(attribute));
			publish.setPublishDate(publishDate);
			publish.setManager(manager);
			publish.setVerifier(verifier);
			publish.setDeployer(deployer);
			publish.setState(Integer.valueOf(state));
			publishDao.updatePublish(publish);
			msg.put("success", "更新成功");
		} catch (Exception e) {
			logger.error(e);
			msg.put("succee", "更新失败");
		}
		return msg;
	}
//	
//	@Transactional(readOnly = true)
//	public PublishDetail getPublishDetails(String suiteName, String publishDate) {
//		PublishDetail publishDetail = publishDao.selectPublishDetail(suiteName, publishDate);
//		List<ProjectChange> projectChanges = projectChangesDao.selectProjectChangeByJiraNo(publishDetail.getJiraNo());
//		publishDetail.setProjectChanges(projectChanges);
//		return publishDetail;
//	}

	public String saveFile(MultipartFile file,String fileName) throws Exception {
		String extName = getExtName(file.getOriginalFilename());
		if (!FileUtil.isExcel(extName)) {
			return null;
		}

		// String fileName = UUID.randomUUID().toString().replace("-",
		// "")+"."+extName;
		Files.copy(file.getInputStream(), new File(FileUtil.EXCEL_DIR + fileName).toPath(),
				StandardCopyOption.REPLACE_EXISTING);
		return FileUtil.DOMAIN + "excel?name=" + fileName;
	}

	public String getExtName(String originFileName) {
		int dotPos = originFileName.lastIndexOf(".");
		if (dotPos < 0) {
			return null;
		}
		return originFileName.substring(dotPos + 1);
	}

}
