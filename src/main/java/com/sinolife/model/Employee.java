package com.sinolife.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
/**
 * 
 * @author Flystar
 *
 */
public class Employee {
	@NotNull
	private int id;
	private String name;
	private String email;
	private int age;
	private int gender;
	private int companyId;
	private int deparntmentId;
	private int spareDay;
	private int role;
	private int state;
	private Date createdDate;
	private Date updatedDate;
	
	public Employee() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getDeparntmentId() {
		return deparntmentId;
	}
	public void setDeparntmentId(int deparntmentId) {
		this.deparntmentId = deparntmentId;
	}
	public int getSpareDay() {
		return spareDay;
	}
	public void setSpareDay(int spareDay) {
		this.spareDay = spareDay;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
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
