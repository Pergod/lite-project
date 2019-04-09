package com.sinolife.model;

import java.io.Serializable;

public class RequirementDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Requirement requirement;
	private Business business;
	private Publish publish ;
	
	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	public Publish getPublish() {
		return publish;
	}
	public void setPublish(Publish publish) {
		this.publish = publish;
	}
	public Requirement getRequirement() {
		return requirement;
	}
	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}
}
