package com.sinolife.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sinolife.model.Business;

@Mapper
public interface BusinessDao {
	String TABLE_NAME ="business";
	String SELECT_FIELDS="id,suite_name,business_desc,tag_color,created_user,updated_user,created_date,updated_date";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where business_desc =#{businessDesc}"})
	public Business selectBusinessByDesc(@Param("businessDesc")String businessDesc);
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id =#{businessId}"})
	public Business selectBusinessById(@Param("businessId")int businessId);
}
