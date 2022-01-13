package com.springSecurityNew.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springSecurityNew.dao.RoleDao;
import com.springSecurityNew.entity.Role;

@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;
	
	public Role createNewRole(Role role) {
		return roleDao.save(role); 
	}
}
