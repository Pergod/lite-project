package com.sinolife.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sinolife.model.JIRA;

@Mapper
public interface JIRADao {
	
	@Select("select j.* From employee e,jira j where j.developer_id = e.id and e.name =#{name}")
	List<JIRA> selectJIRAsByDeveloperEmployeeName(@Param("name") String name);
	
	@Select("select j.* From employee e,jira j where j.tester_id = e.id and e.name =#{name}")
	List<JIRA> selectJIRAsByTesterEmployeeName(@Param("name") String name);
	
	@Select("select j.* From employee e,jira j where j.reporter_id = e.id and e.name =#{name}")
	List<JIRA> selectJIRAsByReporterEmployeeName(@Param("name") String name);
}
