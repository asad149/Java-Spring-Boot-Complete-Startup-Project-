package com.springSecurityNew.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.springSecurityNew.entity.Employee;
import com.springSecurityNew.model.EmployeeRequest;
import com.springSecurityNew.model.GeneralResponse;
import com.springSecurityNew.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired GeneralResponse response;

	
	 @GetMapping("/employee")
	 @PreAuthorize("hasRole('Admin')")
	public ResponseEntity<GeneralResponse> getAllEmployee(@RequestHeader(name = "Authorization", required = true) String authorization){
		try {
			log.info("Into Getting All Employee Controller");
			
		List<Employee> employee = employeeService.getAllEmployee();
	
		response.setResponseCode(HttpStatus.OK);
		response.setResponseDesc("Success");
		response.setObject(employee);
		
		log.info("Getting All Employee Data "+ employee );
		
		return ResponseEntity.ok().body(response);
		}
		catch(Exception ex) {
		
			log.error(ex.getMessage());
			response.setResponseCode(HttpStatus.BAD_REQUEST);
			response.setResponseDesc("Failed To Get Data");
			response.setObject(null);
			log.error(response.toString());
			return ResponseEntity.ok().body(response);
		}
		
		
	}
	
	 
	 @GetMapping("/employee/{id}")
	 @PreAuthorize("hasRole('Admin')")
	public ResponseEntity<GeneralResponse> getEmployeeById(@PathVariable int id,@RequestHeader(name = "Authorization", required = true) String authorization) {
		
		 try {
				log.info("Into Getting One Employee Controller");
				
			   Employee employee =employeeService.getEmployeeById(id);
			   
   			    response.setResponseCode(HttpStatus.OK);
				response.setResponseDesc("Success");
				response.setObject(employee);
				log.info("Getting One Employee Data "+ employee );
			    return ResponseEntity.ok().body(response);
		 }
		 catch(Exception ex) {
				log.error(ex.getMessage());
				response.setResponseCode(HttpStatus.BAD_REQUEST);
				response.setResponseDesc("Failed To Get Data");
				response.setObject(null);
				log.error("Error While Fetching Employee "+response.toString());
				return ResponseEntity.ok().body(response);
		 }
		 
	}
	
	 

//	@GetMapping("/employee")
//	 @PreAuthorize("hasRole('Admin')")
//	public List<Employee> getAllEmployee(@RequestHeader(name = "Authorization", required = true) String authorization) {
//		return employeeService.getAllEmployee();
//	}
	
	
//	@GetMapping("/employee/{id}")
//	 @PreAuthorize("hasRole('Admin')")
//	public Employee getEmployeeById(@PathVariable int id,@RequestHeader(name = "Authorization", required = true) String authorization) {
//		
//		return employeeService.getEmployeeById(id);
//	}
	
	
	@PostMapping("/employee")
	 @PreAuthorize("hasRole('Admin')")
	public Employee addEmployee(@RequestBody EmployeeRequest employee,@RequestHeader(name = "Authorization", required = true) String authorization) {
		return employeeService.addEmployee(employee);
	}
	

	@PutMapping("/employee")
	@PreAuthorize("hasRole('Admin')")
	public Employee updateEmployee(@RequestBody EmployeeRequest employee,@RequestHeader(name = "Authorization", required = true) String authorization) {
		return employeeService.addEmployee(employee);
	}
	
	
	@DeleteMapping("/employee/{id}")
	 @PreAuthorize("hasRole('Admin')")
	public String deleteEmployeeById(@PathVariable int id,@RequestHeader(name = "Authorization", required = true) String authorization) {
		
		return employeeService.deleteEmployeeById(id);
	}
	
	
}
