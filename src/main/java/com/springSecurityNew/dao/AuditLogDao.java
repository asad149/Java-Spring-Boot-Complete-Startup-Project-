package com.springSecurityNew.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springSecurityNew.entity.AuditLog;

public interface AuditLogDao extends JpaRepository<AuditLog, Long>{

}
