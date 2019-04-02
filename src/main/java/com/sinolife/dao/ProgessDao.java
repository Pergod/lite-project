package com.sinolife.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sinolife.model.Progress;

@Mapper
public interface ProgessDao {
	String TABLE_NAME ="progress";
	String SELECT_FIELDS="id,progress_desc,tag_color,created_user,updated_user,created_date,updated_date";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id =#{id}"})
	Progress selectProgressById(@Param("id") int id);
}
