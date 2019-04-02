package com.sinolife.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sinolife.model.Attribute;

@Mapper
public interface AttributeDao {
	String TABLE_NAME ="attribute";
	String SELECT_FIELDS="id,attribute_desc,created_user,updated_user,created_date,updated_date";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where attribute_desc =#{attributeDesc}"})
	public Attribute selectAttributeByDesc(@Param("attributeDesc")String attributeDesc);
}
