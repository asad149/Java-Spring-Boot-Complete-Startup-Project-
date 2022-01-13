package com.springSecurityNew.model;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeRequest {

	 @NotEmpty(message = "Please provide a name")
	private String name;
	 @NotEmpty(message = "Please provide a gender")
	 @Size(min = 4, max = 6, message = "Invalid Length")
	private String gender;
	 @NotEmpty(message = "Please provide a department")
	private String department;

}
