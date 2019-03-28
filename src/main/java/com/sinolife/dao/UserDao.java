package com.sinolife.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sinolife.model.User;


@Mapper
public interface UserDao {
	String TABLE_NAME ="user";
	String SELECT_FIELDS="userName,email,wxNickName,age,gender,center,department,spareDay,role";
	String INSERT_FIELDS = "name, password,deparntment,grade";
	
	
	@Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,  ") values (#{name},#{password},#{deparntment},#{grade})"})
	public void addUser(User user);
	
	@Select({"select ",SELECT_FIELDS,"from",TABLE_NAME," where","wx_nick_name=#{wxNickName}"})
	public User selectUserByWx(String wxNickName);
	
	@Select({"select ",SELECT_FIELDS,"from",TABLE_NAME," where","user_name=#{userName} and password=#{password}"})
	public User selectUserByNameAndPwd(String userName,String passWord);
}
