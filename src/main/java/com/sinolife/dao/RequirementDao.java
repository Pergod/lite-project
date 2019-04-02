package com.sinolife.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sinolife.model.Requirement;

/**
 * 需求Dao
 * @author Flystar
 *
 */
@Mapper
public interface RequirementDao {
	String TABLE_NAME = "requirement";
	String SELECT_FIELDS = "id,publish_id,business_id,jira_no,jira_desc,developer,reporter,tester,belong_center,belong_department,manpower,work_day,state,created_date,updated_date";

	
	@Select({ "select ",SELECT_FIELDS," from ", TABLE_NAME, " where publish_id=#{publishId}" })
	List<Requirement> selectAllRequirement(@Param("publishId")int publishId);

	@Select({ "select ",SELECT_FIELDS," from ", TABLE_NAME, " where publish_id=#{publishId} and state=#{state}" })
	List<Requirement> selectRequirementStateCount(@Param("publishId")int publishId,@Param("state")int state);
	
	@Update({"update ", TABLE_NAME, " set jira_no=#{jiraNo},business_id=#{businessId},jira_desc=#{jiraDesc}, state=#{state},developer=#{developer},reporter=#{reporter},tester=#{tester},reporter=#{reporter},manpower=#{manpower},work_day=#{workDay},updated_date=#{updatedDate},updated_user=#{updatedUser} where id=#{id}"})
    void updateRequirement(Requirement requirement);
}