package com.springSecurityNew.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springSecurityNew.entity.AppUser;

@Repository
public interface UserDao extends JpaRepository<AppUser, String> {

}
