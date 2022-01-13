package com.springSecurityNew.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springSecurityNew.entity.AppUser;
import com.springSecurityNew.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }
	
	@PostMapping("/registerNewUser")
	public AppUser registerNewUser(@RequestBody AppUser user) {
		return userService.registerNewUser(user);
	}
	
	

  
	@GetMapping("/forAdmin")
    @PreAuthorize("hasRole('Admin')")
	public String forAdmin(@RequestHeader(name = "Authorization", required = true) String authorization) {

		try {
			log.info("This End Point is only accessible for Admin");

			return "This End Point is only accessible for Admin";	
		}
		catch(Exception ex) {
			log.error("Error"+ ex.getMessage());
		}
		return null;
	}
	

	@GetMapping("/forUser")
	 @PreAuthorize("hasRole('User')")
//	@PreAuthorize("hasAnyRole('Admin','User')")
	public String forUser(@RequestHeader(name = "Authorization", required = true) String authorization) {

		try {
			log.info("This End Point is only accessible for User");

			return "This End Point is only accessible for User";	
		}
		catch(Exception ex) {
			log.error("Error"+ ex.getMessage());
		}
		return null;
	}

}
