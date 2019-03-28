package com.sinolife.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sinolife.model.PageBean;
import com.sinolife.model.Publish;
import com.sinolife.service.PublishService;
/**
 * 主页，查询排期信息
 * @author Flystar
 *
 */
@Controller
public class IndexController {
	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	@Autowired
	private PublishService publishService;
	
	/**
	 * 查询排期列表 - 分页
	 * @param currentpage	当前页
	 * @param pagesize		页面显示数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path = {"/index","/"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index(@RequestParam(value = "currentpage",defaultValue="1") int currentpage,
						@RequestParam(value = "pagesize",defaultValue="5") int pagesize) {
		PageBean pageBean = publishService.getPublishOffset(currentpage,pagesize);
		List<Publish> publishs = pageBean.getList();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<Publish> result = new ArrayList<Publish>();
		for (Publish publish : publishs) {
			logger.info(publish.getPublishDate());
			result.add(publish);
		}
		map.put("publishs", result);
		String json = JSON.toJSONString(map);
		logger.info(json);
        return json;
    }
}	
