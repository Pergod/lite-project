package com.sinolife.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinolife.controller.IndexController;
import com.sinolife.dao.UserDao;
import com.sinolife.model.User;

@Service
public class UserService {
	private static final Logger logger = Logger.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;

	public Map<String, Object> register(
			String userName, String passWord,
			String comfirmPwd, String workarea,String role) {
		
			Map<String, Object> msg = new HashMap<String,Object>();
			try {
			if (StringUtils.isBlank(userName)) {
				msg.put("error", "用户名不能为空");
				return msg;
			}
			if (StringUtils.isBlank(passWord)) {
				msg.put("error", "密码不能为空");
				return msg;
			}
			if (StringUtils.isBlank(comfirmPwd)) {
				msg.put("error", "确认密码不能为空");
				return msg;
			}
			
			if (!passWord.equals(comfirmPwd)) {
				msg.put("error", "密码不匹配");
				return msg;
			}
			String center = null;
			String department = null;
			if (StringUtils.isNotBlank(workarea)) {
				center = workarea.split("-")[0];
				department = workarea.split("-")[1];
			}
			User user = new User();
			user.setUserName(userName);
			user.setPassWord(passWord);
			user.setDepartment(department);
			user.setCenter(center);
			user.setRole(role);
			userDao.addUser(user);
			msg.put("success", "注册成功");
			return msg;
		} catch (Exception e) {
			logger.error(e);
			msg.put("error", "注册异常");
			return msg;
		}
	}
	
	public Map<String, Object> loginByWx(String wxNickName){
		Map<String, Object> msg = new HashMap<String,Object>();
		try {
			User user = userDao.selectUserByWx(wxNickName);
			if (user == null) {
				msg.put("error", "微信登录失败");
			}
			msg.put("success", "微信登录成功");
			return msg;
		} catch (Exception e) {
			logger.error(e);
			msg.put("error", "微信登录异常");
			return msg;
		}
	}
	
	public Map<String, Object> login(String userName,String passWord){
		Map<String, Object> msg = new HashMap<String,Object>();
		try {
			User user = userDao.selectUserByNameAndPwd(userName,passWord);
			if (user == null) {
				msg.put("error", "登录失败");
			}
			msg.put("success", "登录成功");
			return msg;
		} catch (Exception e) {
			logger.error(e);
			msg.put("error", "登录异常");
			return msg;
		}
	}
}
