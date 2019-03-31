package com.sinolife.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sinolife.Application;
import com.sinolife.dao.RequirementDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TestService {

	@Autowired
	RequirementDao requirementDao;

	@Test
	public void test01() {
		requirementDao.selectRequirementStateCount(1,1);
	}
}
