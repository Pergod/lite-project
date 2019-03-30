package com.sinolife.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.sinolife.model.PageBean;
import com.sinolife.model.Publish;
import com.sinolife.model.PublishDetail;
import com.sinolife.service.PublishService;
import com.sinolife.util.JSONUtil;
/**
 * 排期页
 * @author Flystar
 *
 */
@Controller
public class PublishController {
	private static final Logger logger = Logger.getLogger(PublishController.class);
	
	@Autowired
	private PublishService publishService;   
	
	/**
	 * 查询排期列表 
	 * @param currentpage	当前页
	 * @param pagesize		页面显示数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path = {"/publish"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index(@RequestParam(value = "currentpage",defaultValue="1") int currentpage,
						@RequestParam(value = "pagesize",defaultValue="10") int pagesize) {
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
	
	
	/*
	 * 查询条线排期详情
	 */
	@RequestMapping(path = {"/publishDetails"}, method = {RequestMethod.GET})
    @ResponseBody
    public String suiteNamePublishDetails(@RequestParam(value = "suiteName") String suiteName,
    							@RequestParam(value = "publishDate") String publishDate) {
		PublishDetail publishDetail = publishService.getPublishDetails(suiteName, publishDate);
		Map<String, PublishDetail> results = new LinkedHashMap<String, PublishDetail>();
		results.put("publishDetail", publishDetail);
		String json = JSON.toJSONString(results);
		return json;
	}
	/**
	 * 上传并解析excel文件
	 * @param excelFile
	 * @return
	 */
	@RequestMapping(path = { "/uploadPublish" }, method = {RequestMethod.POST })
	@ResponseBody
	public String uploadExcel(@RequestParam("file") MultipartFile excelFile,@RequestParam("fileName") String fileName) {
		try {
			String fileUrl = publishService.saveFile(excelFile,fileName);
			if (fileUrl == null) {
				return JSONUtil.getJSONString(1,"上传失败");
			}
			logger.info("fileUrl="+fileUrl);
			//解析excel文件-- 事务操作
			return JSONUtil.getJSONString(0,fileUrl);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return JSONUtil.getJSONString(1,"上传失败");
		}
	}
}
