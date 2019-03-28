package com.sinolife.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sinolife.model.Department;

@Mapper
public interface DepartmentDao {
	String TABLE_NAME ="Department";
	String SELECT_FIELDS="id,company_id,department_desc";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id =#{id}"})
	Department selectDepartmentById(int id);
}
