package com.sinolife.model;

import java.util.Date;

/**
 * 项目更改点
 * @author Flystar
 *
 */
public class ProjectChange {
	
	private int id;
	private String projectId;
	private String changesDesc;
	private String changesId;
	private Date createdDate;
	private Date updatedDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getChangesDesc() {
		return changesDesc;
	}
	public void setChangesDesc(String changesDesc) {
		this.changesDesc = changesDesc;
	}
	public String getChangesId() {
		return changesId;
	}
	public void setChangesId(String changesId) {
		this.changesId = changesId;
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
