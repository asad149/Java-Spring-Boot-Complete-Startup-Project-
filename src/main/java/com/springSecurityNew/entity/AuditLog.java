package com.springSecurityNew.entity;


import java.util.Date;

import javax.persistence.Column;
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
@Entity
@Table(name="audit_log")
public class AuditLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;


	@Column(name = "ip_address", length = 50)
	private String ipAddress;

	@Column(name = "request_date")
	private Date requestDate;

	@Column(name = "request_headers")
	private String requestHeaders;

	@Column(name = "response_date")
	private Date responseDate;

	@Column(name = "response_headers")
	private String responseHeaders;


	@Column(name = "rrn", length = 100)
	private String rrn;

	@Column(name = "stan", length = 100)
	private String stan;


	@Column(name = "response_code", length = 100)
	private String responseCode;
	
	@Column(name = "response_desc", length = 100)
	private String responseDesc;

	public AuditLog(String ipAddress, Date requestDate, String requestHeaders, Date responseDate,
			String responseHeaders, String rrn, String stan, String responseCode, String responseDesc) {
		super();
		this.ipAddress = ipAddress;
		this.requestDate = requestDate;
		this.requestHeaders = requestHeaders;
		this.responseDate = responseDate;
		this.responseHeaders = responseHeaders;
		this.rrn = rrn;
		this.stan = stan;
		this.responseCode = responseCode;
		this.responseDesc = responseDesc;
	}
	
	
		
}
