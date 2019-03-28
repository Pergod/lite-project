package com.sinolife.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * 排期
 * @author Flystar
 *
 */
public class Publish {
	private int id;
	private String publishDesc;
	private int attribute;
	
	@NotNull
	private String publishDate;
	private int managerId;
	private int verifierId;
	private int deployerId;
	private int state;
	private Date createdDate;
	private Date updatedDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPublishDesc() {
		return publishDesc;
	}
	public void setPublishDesc(String publishDesc) {
		this.publishDesc = publishDesc;
	}
	public int getAttribute() {
		return attribute;
	}
	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public int getVerifierId() {
		return verifierId;
	}
	public void setVerifierId(int verifierId) {
		this.verifierId = verifierId;
	}
	public int getDeployerId() {
		return deployerId;
	}
	public void setDeployerId(int deployerId) {
		this.deployerId = deployerId;
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
