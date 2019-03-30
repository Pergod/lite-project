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
}
