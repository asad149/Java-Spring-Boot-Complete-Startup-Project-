package com.springSecurityNew.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springSecurityNew.entity.Employee;
import com.springSecurityNew.model.EmployeeRequest;

public interface EmployeeDao extends JpaRepository<Employee, Integer> {



}
