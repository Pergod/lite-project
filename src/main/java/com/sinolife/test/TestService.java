package com.sinolife.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sinolife.Application;
import com.sinolife.dao.ProjectChangesDao;
import com.sinolife.dao.PublishDao;
import com.sinolife.model.PageBean;
import com.sinolife.model.ProjectChange;
import com.sinolife.model.Publish;
import com.sinolife.model.PublishDetail;
import com.sinolife.service.PublishService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TestService {
	
	@Autowired
	private PublishService publishService;
	
	@Autowired
	private PublishDao publishDao;
	
	@Autowired
	ProjectChangesDao projectChangesDao;
	
	@Test
	public void test1() {
		PageBean pageBean = publishService.getPublishOffset(1, 5);
		List<Publish> publishs = pageBean.getList();
		for (Publish publish : publishs) {
			System.out.println(publish.getId());
		}
	}
	
	@Test
	public void test2() {
		PublishDetail publishDetail = publishService.getPublishDetails("sx", "2019-02-14");
		List<ProjectChange> projectChanges = publishDetail.getProjectChanges();
		for (ProjectChange projectChange : projectChanges) {
			System.out.println(projectChange.getProjectId() + ":" + projectChange.getChangesDesc());
		}
	}
	
	@Test
	public void test3() {
		List<ProjectChange> projectChanges = projectChangesDao.selectProjectChangeByJiraNo("DMP-12345");
		for (ProjectChange projectChange : projectChanges) {
			System.out.println(projectChange.getProjectId() +":"+projectChange.getChangesDesc());
		}
	}
	
	@Test
	public void test4() {
		Publish publish = publishService.getPublishByDate("2019-02-14");
		System.out.println(publish.getPublishDesc());
	}
}
