package com.springSecurityNew.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@Table(name = "auditlog_details")
public class AuditLogDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "audit_log_id")
	private AuditLog auditlogId;

	@Column(name = "request_body")
	@Lob
	private String requestBody;
	
	@Column(name = "response_body")
	private String responseBody;

	public AuditLogDetails(AuditLog auditlogId, String requestBody, String responseBody) {
		super();
		this.auditlogId = auditlogId;
		this.requestBody = requestBody;
		this.responseBody = responseBody;
	}
	
	
}