package com.sinolife.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sinolife.model.State;

@Mapper
public interface StateDao {
	String TABLE_NAME ="state";
	String SELECT_FIELDS="id,state_desc,tag_color,created_date,updated_date,created_user,updated_user";

	@Select({"select ",SELECT_FIELDS,"from",TABLE_NAME," where","state_desc=#{stateDesc}"})
	public State selectStateByDesc(@Param("stateDesc") String stateDesc);
}
