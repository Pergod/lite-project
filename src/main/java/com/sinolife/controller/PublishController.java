package com.sinolife.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sinolife.model.HostHolder;
import com.sinolife.service.PublishService;
import com.sinolife.util.JSONUtil;

/**
 * 排期相关Controller
 * 
 * @author Flystar
 *
 */
@Controller
public class PublishController {
	private static final Logger logger = Logger.getLogger(PublishController.class);

	@Autowired
	private PublishService publishService;
	
	@Autowired
	HostHolder hostHolder;

	/**
	 * 查询排期列表
	 * @param currentpage 当前页
	 * @param pagesize  页面显示数
	 * @return
	 */
	@RequestMapping(path = { "/publish" }, method = { RequestMethod.GET })
	@ResponseBody
	public String Publish(@RequestParam(value = "currentpage", defaultValue = "1") int currentpage,
			@RequestParam(value = "pagesize", defaultValue = "10") int pagesize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = publishService.getPublishOffset(currentpage, pagesize);
		if (map.containsKey("success")) {
			logger.info(JSONUtil.getJSONString(0, map));
			return JSONUtil.getJSONString(0, map);
		} else {
			return JSONUtil.getJSONString(1, map);
		}
	}

	/**
	 * 排期更新
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(path = { "/updatePublish" }, method = { RequestMethod.POST })
	@ResponseBody
	public String UpdatePublish(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String,Object>();
		String id = request.getParameter("id");
		String publishDate = request.getParameter("publishDate");
		String manager = request.getParameter("manager");
		String verifier = request.getParameter("verifier");
		String deployer = request.getParameter("deployer");
		String progress = request.getParameter("progress");
		String attribute = request.getParameter("attribute");
		String updateUser = hostHolder.getUser().getUserName();
		map = publishService.UpdatePublish(id,attribute,publishDate,manager,verifier,deployer,progress,updateUser);
		if (map.containsKey("success")) {
			logger.info(JSONUtil.getJSONString(0, map));
			return JSONUtil.getJSONString(0, map);
		} else {
			return JSONUtil.getJSONString(1, map);
		}
	}

	/**
	 * 上传并解析excel文件
	 * 
	 * @param excelFile
	 * @return
	 */
	@RequestMapping(path = { "/uploadPublish" }, method = { RequestMethod.POST })
	@ResponseBody
	public String uploadExcel(@RequestParam("file") MultipartFile excelFile,
			@RequestParam("fileName") String fileName) {
		try {
			String fileUrl = publishService.saveFile(excelFile, fileName);
			if (fileUrl == null) {
				return JSONUtil.getJSONString(1, "上传失败");
			}
			logger.info("fileUrl=" + fileUrl);
			// 解析excel文件-- 事务操作
			return JSONUtil.getJSONString(0, fileUrl);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return JSONUtil.getJSONString(1, "上传失败");
		}
	}
}
