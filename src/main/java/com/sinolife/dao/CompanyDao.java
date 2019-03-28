package com.sinolife.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sinolife.model.Company;

@Mapper
public interface CompanyDao {
	String TABLE_NAME ="Company";
	String SELECT_FIELDS="id,company_desc";
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id =#{id}"})
	Company selectCompanyById(int id);
}
