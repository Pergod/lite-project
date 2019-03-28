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
	
	/*
	 * 添加排期
	 */
	@RequestMapping(path = {"/addPublish"}, method = {RequestMethod.POST})
    public void addPublish(@RequestParam(value = "publishDate") String publishDate,
						     @RequestParam(value = "attribute",defaultValue="0") int attribute,
						     @RequestParam(value = "managerId") int managerId,
						     @RequestParam(value = "verifierId") int verifierId,
						     @RequestParam(value = "deployerId") int deployerId) {
		Publish publish = new Publish();
		publish.setAttribute(attribute);
		publish.setPublishDate(publishDate);
		publish.setManagerId(managerId);
		publish.setVerifierId(verifierId);
		publish.setDeployerId(deployerId);
		publish.setState(0);
		publish.setCreatedDate(new Date());
		publish.setUpdatedDate(new Date());
		publishService.addPublish(publish);
    }
	
	/*
	 * 查询排期
	 */
	@RequestMapping(path = {"/publish"}, method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String publish(@RequestParam(value = "publishDate") String publishDate,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
	    List<Publish> result = new ArrayList<Publish>();
		if (StringUtils.isBlank(publishDate)) {
			map.put("error", "请输入日期");
			String json = JSON.toJSONString(map);
			logger.info(json);
			return json;
		}
		Publish publish = publishService.getPublishByDate(publishDate);
		if (publish==null) {
			map.put("error", "未查询到排期结果");
			String json = JSON.toJSONString(map);
			logger.info(json);
			return json;
		}else{
			result.add(publish);
			map.put("publishs", result);
		}
		String json = JSON.toJSONString(map);
		logger.info("json="+json);
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
	public String uploadImage(@RequestParam("file") MultipartFile excelFile) {
		try {
			String fileUrl = publishService.saveFile(excelFile);
			if (fileUrl == null) {
				return JSONUtil.getJSONString(1,"上传失败");
			}
			logger.info("fileUrl="+fileUrl);
			//解析excel文件
			return JSONUtil.getJSONString(0,fileUrl);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return JSONUtil.getJSONString(1,"上传失败");
		}
	}
}
