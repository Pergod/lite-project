package com.sinolife.model;

import java.util.Date;
/**
 * 需求
 * @author Flystar
 *
 */
public class JIRA {
	private int id;
	private String jiraNo;
	private String jiraDesc;
	private int developerId;
	private int reporterId;
	private int testerId;
	private int companyId;
	private int departmentId;
	private int publishId;
	private int businessId;
	private int manpowerInput;
	private int workDay;
	private int state;
	private Date createdDate;
	private Date updatedDate;
	
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
	public int getDeveloperId() {
		return developerId;
	}
	public void setDeveloperId(int developerId) {
		this.developerId = developerId;
	}
	public int getReporterId() {
		return reporterId;
	}
	public void setReporterId(int reporterId) {
		this.reporterId = reporterId;
	}
	public int getTesterId() {
		return testerId;
	}
	public void setTesterId(int testerId) {
		this.testerId = testerId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public int getPublishId() {
		return publishId;
	}
	public void setPublishId(int publishId) {
		this.publishId = publishId;
	}
	public int getBusinessId() {
		return businessId;
	}
	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}
	public int getManpowerInput() {
		return manpowerInput;
	}
	public void setManpowerInput(int manpowerInput) {
		this.manpowerInput = manpowerInput;
	}
	public int getWorkDay() {
		return workDay;
	}
	public void setWorkDay(int workDay) {
		this.workDay = workDay;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
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
