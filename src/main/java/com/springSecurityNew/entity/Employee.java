package com.springSecurityNew.entity;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name="Employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String gender;
	private String department;
	
	public Employee(String name, String gender, String department) {
		super();
		this.name = name;
		this.gender = gender;
		this.department = department;
	}
	

	

	
	
}
