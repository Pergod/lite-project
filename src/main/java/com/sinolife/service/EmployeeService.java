package com.sinolife.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinolife.dao.CompanyDao;
import com.sinolife.dao.DepartmentDao;
import com.sinolife.dao.EmployeeDao;
import com.sinolife.dao.JIRADao;
import com.sinolife.model.Company;
import com.sinolife.model.Department;
import com.sinolife.model.Employee;
import com.sinolife.model.EmployeeDetail;
import com.sinolife.model.JIRA;

@Service
public class EmployeeService {
	private static final int DEVELOPER=0; 
	private static final int TESTER=1; 
	private static final int REPORTER=2; 
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	JIRADao jiraDao;
	
	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	DepartmentDao departmentDao;
	
	public EmployeeDetail getEmployeeDetail(int employeeId){
		Employee employee = employeeDao.selectEmployeeById(employeeId);
		Company company = companyDao.selectCompanyById(employee.getCompanyId());
		Department department = departmentDao.selectDepartmentById(employee.getDeparntmentId());
		EmployeeDetail employeeDetail = new EmployeeDetail();
		employeeDetail.setEmployee(employee);
		employeeDetail.setCompany(company);
		employeeDetail.setDepartment(department);
		List<JIRA> jiras = new ArrayList<JIRA>();
		int role = employee.getRole();
		String name = employee.getName();
		if (DEVELOPER == role) {
			jiras = jiraDao.selectJIRAsByDeveloperEmployeeName(name);
		}else if (TESTER == role) {
			jiras = jiraDao.selectJIRAsByTesterEmployeeName(name);
		}else if (REPORTER == role) {
			jiras = jiraDao.selectJIRAsByReporterEmployeeName(name);
		}
		employeeDetail.setJiras(jiras);
		return employeeDetail;
	}
	
}
