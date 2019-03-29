package com.sinolife.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sinolife.model.User;


@Mapper
public interface UserDao {
	String TABLE_NAME ="user";
	String SELECT_FIELDS="user_name,email,wx_nick_name,age,gender,center,department,spare_day,role";
	String INSERT_FIELDS = "user_name, password,email,gender,center,department,role,created_date,updated_date";
	
	@Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,  ") values (#{userName},#{password},#{email},#{gender},#{center},#{department},#{role},#{createdDate},#{updatedDate})"})
	public void addUser(User user);
	
	@Select({"select ",SELECT_FIELDS,"from",TABLE_NAME," where","wx_nick_name=#{wxNickName}"})
	public User selectUserByWx(@Param("wxNickName") String wxNickName);
	
	@Select({"select ",SELECT_FIELDS,"from",TABLE_NAME," where ","user_name=#{userName} and password=#{password}"})
	public User selectUserByNameAndPwd(@Param("userName") String userName, @Param("password") String passWord);
	
	//更新微信号
    @Update({"update ", TABLE_NAME, " set wx_nick_name=#{wxNickName} where user_name=#{userName}"})
	public void insertWxNickName(@Param("wxNickName") String wxNickName,@Param("userName") String userName);
}
