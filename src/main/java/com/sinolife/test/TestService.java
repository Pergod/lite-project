package com.sinolife.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sinolife.Application;
import com.sinolife.dao.RequirementDao;
import com.sinolife.service.RedisService;
import com.sinolife.service.RequirementService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TestService {

	@Autowired
	RequirementDao requirementDao;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	RequirementService requirementService;

	@Test
	public void test01() {
		long userinfoTime = System.currentTimeMillis(); 
		Map<String, Object> map = requirementService.getRequirementDetails(1);
		System.out.println(Thread.currentThread() + " 接口调用完毕" + (System.currentTimeMillis() - userinfoTime)+":result"+map);
	}
	
	@Test
	public void test02() {
		Map<String, Object> map = requirementService.getRequirementDetails(1);
		redisService.set("111", map);
		System.out.println(redisService.get("111"));
	}
}
