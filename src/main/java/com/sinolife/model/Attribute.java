package com.sinolife.model;

import java.util.Date;

public class Attribute {
	private int id;
	private String attrbuteDesc;
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
	public String getAttrbuteDesc() {
		return attrbuteDesc;
	}
	public void setAttrbuteDesc(String attrbuteDesc) {
		this.attrbuteDesc = attrbuteDesc;
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
