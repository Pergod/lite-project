package com.sinolife.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinolife.dao.UserDao;
import com.sinolife.model.User;

/**
 * 用户相关Service
 * @author Flystar
 *
 */
@Service
public class UserService {
	private static final Logger logger = Logger.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;
	
	/**
	 * 注册
	 * @param userName
	 * @param password
	 * @param comfirmPwd
	 * @param email
	 * @param gender
	 * @param workarea
	 * @param role
	 * @return
	 */
	public Map<String, Object> register(
			String userName, String password,String comfirmPwd,String email,
			String gender, String workarea,String role) {
		
			Map<String, Object> msg = new HashMap<String,Object>();
			try {
			if (StringUtils.isBlank(userName)) {
				msg.put("error", "用户名不能为空");
				return msg;
			}
			if (StringUtils.isBlank(password)) {
				msg.put("error", "密码不能为空");
				return msg;
			}
			if (StringUtils.isBlank(comfirmPwd)) {
				msg.put("error", "确认密码不能为空");
				return msg;
			}
			
			if (!password.equals(comfirmPwd)) {
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
			user.setDepartment(department);
			user.setPassword(password);
			user.setCenter(center);
			user.setRole(role);
			user.setEmail(email);
			user.setCreatedDate(new Date());
			user.setUpdatedDate(new Date());
			if (StringUtils.isNotBlank(gender)) {
				user.setGender(Integer.valueOf(gender));
			}
			userDao.addUser(user);
			msg.put("success", "注册成功");
			msg.put("user", user);
			return msg;
		} catch (Exception e) {
			logger.error(e);
			msg.put("error", "注册异常");
			return msg;
		}
	}
	
	/**
	 * 微信号登录
	 * @param wxNickName
	 * @return
	 */
	public Map<String, Object> loginByWx(String wxNickName){
		Map<String, Object> msg = new HashMap<String,Object>();
		try {
			User user = userDao.selectUserByWx(wxNickName);
			if (user == null) {
				msg.put("error", "微信登录失败，账户未绑定");
				return msg;
			}
			msg.put("success", "微信登录成功");
			msg.put("user", user);
			return msg;
		} catch (Exception e) {
			logger.error(e);
			msg.put("error", "微信登录异常");
			return msg;
		}
	}
	
	/**
	 * 用户名，密码登录
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public Map<String, Object> login(String userName,String passWord){
		Map<String, Object> msg = new HashMap<String,Object>();
		try {
			User user = userDao.selectUserByNameAndPwd(userName,passWord);
			if (user == null) {
				msg.put("error", "登录失败");
			}
			msg.put("success", "登录成功");
			msg.put("user", user);
			return msg;
		} catch (Exception e) {
			logger.error(e);
			msg.put("error", "登录异常");
			return msg;
		}
	}
	
	/**
	 * 账户绑定微信
	 * @param wxNickName
	 * @return
	 */
	public Map<String, Object> bind(String wxNickName,String userName){
		Map<String, Object> msg = new HashMap<String,Object>();
		try {
			if (StringUtils.isBlank(wxNickName)) {
				msg.put("error", "获取微信号失败");
				return msg;
			}
			userDao.insertWxNickName(wxNickName, userName);
			msg.put("success", "绑定成功");
		} catch (Exception e) {
			logger.error(e);
			msg.put("error", "绑定异常");
		}
		return msg;
	}
}
