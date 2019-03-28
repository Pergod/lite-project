package com.sinolife.model;

/**
 * 
 * @author Flystar
 *
 */
import java.util.List;
public class EmployeeDetail {
	
	private User employee;
	private Company company;
	private Department department;
	private List<JIRA> jiras;
	public User getEmployee() {
		return employee;
	}
	public void setEmployee(User employee) {
		this.employee = employee;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public List<JIRA> getJiras() {
		return jiras;
	}
	public void setJiras(List<JIRA> jiras) {
		this.jiras = jiras;
	}
	
//	private int id;
//	private String name;
//	private String email;
//	private int age;
//	private String gender;
//	private String companyDesc;
//	private String deparntmentDesc;
//	private int spareDay;
//	private String role;
//	private int state;
//	private Date createdDate;
//	private Date updatedDate;
//	List<JIRA> jiras;
//	
//	public List<JIRA> getJiras() {
//		return jiras;
//	}
//	public void setJiras(List<JIRA> jiras) {
//		this.jiras = jiras;
//	}
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public int getAge() {
//		return age;
//	}
//	public void setAge(int age) {
//		this.age = age;
//	}
//	public String getGender() {
//		return gender;
//	}
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//	public String getCompanyDesc() {
//		return companyDesc;
//	}
//	public void setCompanyDesc(String companyDesc) {
//		this.companyDesc = companyDesc;
//	}
//	public String getDeparntmentDesc() {
//		return deparntmentDesc;
//	}
//	public void setDeparntmentDesc(String deparntmentDesc) {
//		this.deparntmentDesc = deparntmentDesc;
//	}
//	public int getSpareDay() {
//		return spareDay;
//	}
//	public void setSpareDay(int spareDay) {
//		this.spareDay = spareDay;
//	}
//	public String getRole() {
//		return role;
//	}
//	public void setRole(String role) {
//		this.role = role;
//	}
//	public int getState() {
//		return state;
//	}
//	public void setState(int state) {
//		this.state = state;
//	}
//	public Date getCreatedDate() {
//		return createdDate;
//	}
//	public void setCreatedDate(Date createdDate) {
//		this.createdDate = createdDate;
//	}
//	public Date getUpdatedDate() {
//		return updatedDate;
//	}
//	public void setUpdatedDate(Date updatedDate) {
//		this.updatedDate = updatedDate;
//	}
}
