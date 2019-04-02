package com.sinolife.model;

import java.util.Date;
/**
 * 需求
 * @author Flystar
 *
 */
public class Requirement {
	private int id;
	private int publishId;
	private int businessId;
	private String jiraNo;
	private String jiraDesc;
	private String developer;
	private String reporter;
	private String tester;
	private String belongCenter;
	private String belongDepartment;
	private int manpower;
	private int workDay;
	//是否需要代码评审
	private boolean needReview;
	public boolean isNeedReview() {
		return needReview;
	}
	public void setNeedReview(boolean needReview) {
		this.needReview = needReview;
	}
	private String createdUser;
	private String updatedUser;
	private Date createdDate;
	private Date updatedDate;
	/**
	 * 需求状态
	 * 1:未分配
	 * 2:审批中
	 * 3:开发中
	 * 4:内测中
	 * 5:已移交测试
	 * 6:已完成
	 */
	private int state;
	
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getDeveloper() {
		return developer;
	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public String getTester() {
		return tester;
	}
	public void setTester(String tester) {
		this.tester = tester;
	}
	public String getBelongCenter() {
		return belongCenter;
	}
	public void setBelongCenter(String belongCenter) {
		this.belongCenter = belongCenter;
	}
	public String getBelongDepartment() {
		return belongDepartment;
	}
	public void setBelongDepartment(String belongDepartment) {
		this.belongDepartment = belongDepartment;
	}
	public int getManpower() {
		return manpower;
	}
	public void setManpower(int manpower) {
		this.manpower = manpower;
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
