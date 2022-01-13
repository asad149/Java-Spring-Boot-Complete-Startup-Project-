package com.springSecurityNew.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springSecurityNew.dao.EmployeeDao;
import com.springSecurityNew.entity.Employee;
import com.springSecurityNew.model.EmployeeRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	


	public List<Employee> getAllEmployee() {
	
		try {
			
			log.info("Getting all the Employee  " + employeeDao.findAll() );
		    return employeeDao.findAll();
		
		}
		catch(Exception ex) {
			log.error(ex.getMessage());
		}
		return null;
	}
	 
     public Employee getEmployeeById(int id) {
		
		return employeeDao.findById(id).get();
	}
     public Employee addEmployee(EmployeeRequest employee) {
    	Employee emp = new Employee(employee.getName(),employee.getGender(),employee.getDepartment());
 		return employeeDao.save(emp);
 	}
	
     public String deleteEmployeeById(int id) {
 		
    	 employeeDao.deleteById(id);
 		return "Employee has been deleted " + id;
 	}

	
     
     
}
