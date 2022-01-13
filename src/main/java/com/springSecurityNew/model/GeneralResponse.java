package com.springSecurityNew.model;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Component
public class GeneralResponse {

	
	private HttpStatus responseCode;
	private String responseDesc;
	private Object object;
	
}
