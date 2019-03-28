package com.sinolife.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sinolife.model.ProjectChange;



@Mapper
public interface ProjectChangesDao {
	String TABLE_NAME ="project_changes";
	String SELECT_PROJECT_CHANGES_FIELDS = " id, project_id,changes_desc,changes_id,created_date,updated_date";
	
	@Select({"select ",SELECT_PROJECT_CHANGES_FIELDS,"from ",TABLE_NAME," where id=#{id}"})
	ProjectChange selectChangesDesc(@Param("id") int id);
	
	@Select("select pc.changes_desc,pc.project_id from jira j,project_change pc,jira_project_change jpc where j.jira_no=jpc.jira_no and jpc.jira_project_id = pc.id and j.jira_no=#{jira_no}")
	List<ProjectChange> selectProjectChangeByJiraNo(@Param("jira_no") String jiraNo);
}
