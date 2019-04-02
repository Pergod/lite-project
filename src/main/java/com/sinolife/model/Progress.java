package com.sinolife.model;

import java.util.Date;

/**
 * 进度
 * @author Flystar
 *
 */
public class Progress {
	private int id;
	private String progressDesc;
	private String tagColor;
	private Date createdDate;
	private Date updatedDate;
	private String createdUser;
	private String updatedUser;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProgressDesc() {
		return progressDesc;
	}
	public void setProgressDesc(String progressDesc) {
		this.progressDesc = progressDesc;
	}
	public String getTagColor() {
		return tagColor;
	}
	public void setTagColor(String tagColor) {
		this.tagColor = tagColor;
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
	
	
}
