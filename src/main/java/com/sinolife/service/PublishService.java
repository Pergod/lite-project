package com.sinolife.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sinolife.dao.ProjectChangesDao;
import com.sinolife.dao.PublishDao;
import com.sinolife.model.PageBean;
import com.sinolife.model.Publish;
import com.sinolife.util.FileUtil;

@Service
public class PublishService {
	private static final Logger logger = Logger.getLogger(PublishService.class);

	@Autowired
	private PublishDao publishDao;

	@Autowired
	ProjectChangesDao projectChangesDao;

	public int addPublish(Publish publish) {
		return publishDao.insertPublish(publish);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> getPublishOffset(int startIndex, int pageSize) {
		Map<String, Object> msg = new HashMap<String,Object>();
		PageBean pageBean = new PageBean();
		List<Publish> publishs = publishDao.selectPublishOffset((startIndex - 1) * pageSize, pageSize);
		pageBean.setList(publishs);
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
