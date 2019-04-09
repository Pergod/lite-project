package com.sinolife.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * 排期
 * @author Flystar
 *
 */
public class Publish implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private int progress = 1;
	private String createdUser;
	private String updatedUser;
	private Date createdDate;
	private Date updatedDate;
	//排期下需求
	private List<Requirement> requirements;
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
	public List<Requirement> getRequirements() {
		return requirements;
	}
	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}
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
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
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
