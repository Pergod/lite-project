package com.sinolife.model;

import java.util.Date;
import java.util.List;
/**
 * 排期需求详情
 * @author Flystar
 *
 */
public class PublishDetail {
	private int id;
	private String jiraNo;
	private String jiraDesc;
	private String publishDate;
	private String projectId;
	private String developerName;
	private String reporterName;
	private String testerName;
	private int companyDesc;
	private int departmentDesc;
	private Date createdDate;
	private Date updatedDate;
	private List<ProjectChange> projectChanges;
	public List<ProjectChange> getProjectChanges() {
		return projectChanges;
	}
	public void setProjectChanges(List<ProjectChange> projectChanges) {
		this.projectChanges = projectChanges;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJiraNo() {
		return jiraNo;
	}
	public void setJiraNo(String jiraNo) {
		this.jiraNo = jiraNo;
	}
	public String getJiraDesc() {
		return jiraDesc;
	}
	public void setJiraDesc(String jiraDesc) {
		this.jiraDesc = jiraDesc;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getDeveloperName() {
		return developerName;
	}
	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}
	public String getReporterName() {
		return reporterName;
	}
	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}
	public String getTesterName() {
		return testerName;
	}
	public void setTesterName(String testerName) {
		this.testerName = testerName;
	}
	public int getCompanyDesc() {
		return companyDesc;
	}
	public void setCompanyDesc(int companyDesc) {
		this.companyDesc = companyDesc;
	}
	public int getDepartmentDesc() {
		return departmentDesc;
	}
	public void setDepartmentDesc(int departmentDesc) {
		this.departmentDesc = departmentDesc;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
