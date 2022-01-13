package com.springSecurityNew.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springSecurityNew.entity.AuditLogDetails;

public interface AuditLogDetailsDao extends JpaRepository<AuditLogDetails, Long> {

}
