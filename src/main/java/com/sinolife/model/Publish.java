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
	
	//管理人员
	private String manager;
	//验证人员
	private String verifier;
	//部署人员
	private String deployer;
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
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getVerifier() {
		return verifier;
	}
	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
	public String getDeployer() {
		return deployer;
	}
	public void setDeployer(String deployer) {
		this.deployer = deployer;
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
