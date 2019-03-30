package com.sinolife.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sinolife.dao.ProjectChangesDao;
import com.sinolife.dao.PublishDao;
import com.sinolife.model.PageBean;
import com.sinolife.model.ProjectChange;
import com.sinolife.model.Publish;
import com.sinolife.model.PublishDetail;
import com.sinolife.util.FileUtil;

@Service
public class PublishService {

	@Autowired
	private PublishDao publishDao;

	@Autowired
	ProjectChangesDao projectChangesDao;

	public int addPublish(Publish publish) {
		return publishDao.insertPublish(publish);
	}

	@Transactional(readOnly = true)
	public PageBean getPublishOffset(int startIndex, int pageSize) {
		PageBean pageBean = new PageBean();
		List<Publish> publishs = publishDao.selectPublishOffset((startIndex - 1) * pageSize, pageSize);
		pageBean.setList(publishs);
		pageBean.setCurrentpage(startIndex);
		pageBean.setPagesize(pageSize);
		int totalRecord = publishs.size();
		pageBean.setTotalrecord(totalRecord);
		return pageBean;
	}

	@Transactional(readOnly = true)
	public Publish getPublishByDate(String publishDate) {
		Publish publish = publishDao.selectPublishByDate(publishDate);
		return publish;
	}

	@Transactional(readOnly = true)
	public PublishDetail getPublishDetails(String suiteName, String publishDate) {
		PublishDetail publishDetail = publishDao.selectPublishDetail(suiteName, publishDate);
		List<ProjectChange> projectChanges = projectChangesDao.selectProjectChangeByJiraNo(publishDetail.getJiraNo());
		publishDetail.setProjectChanges(projectChanges);
		return publishDetail;
	}

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
