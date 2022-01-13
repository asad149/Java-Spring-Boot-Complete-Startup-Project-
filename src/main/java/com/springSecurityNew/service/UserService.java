package com.springSecurityNew.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springSecurityNew.dao.RoleDao;
import com.springSecurityNew.dao.UserDao;
import com.springSecurityNew.entity.AppUser;
import com.springSecurityNew.entity.Role;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	

    @Autowired
    private PasswordEncoder passwordEncoder;
	
	
	
    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        AppUser adminUser = new AppUser();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);
        
        AppUser user = new AppUser();
      user.setUserName("asad");
      user.setUserPassword(getEncodedPassword("asad@123"));
      user.setUserFirstName("Asad");
      user.setUserLastName("Arshad");
      Set<Role> userRoles = new HashSet<>();
      userRoles.add(userRole);
      user.setRole(userRoles);
      userDao.save(user);


    }
    
    public AppUser registerNewUser(AppUser user) {
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userDao.save(user);
	}
	
    
    
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
	
}
