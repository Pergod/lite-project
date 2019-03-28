package com.sinolife.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sinolife.model.Employee;

@Mapper
public interface EmployeeDao {
	String TABLE_NAME ="Employee";
	String SELECT_FIELDS="id,name,email,age,gender,company_id,deparntment_id,spare_day,role";
	
//	@Select("select e.name,e.spare_day,e.role,c.company_desc,d.department_desc from employee e ,company c, department d where e.company_id = c.id and e.deparntment_id = d.id and e.name=#{name}")
//	EmployeeDetail selectEmployeeByName(@Param("name") String name);
	
//	@Select("select e.name,e.spare_day,e.role,c.company_desc,d.department_desc from employee e ,company c, department d where e.company_id = c.id and e.deparntment_id = d.id and e.id=#{id}")
//	EmployeeDetail selectEmployeeById(@Param("id") int id);
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id =#{id}"})
	Employee selectEmployeeById(@Param("id") int id);
	
	@Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name =#{name}"})
	Employee selectEmployeeByName(@Param("name") String name);
}
