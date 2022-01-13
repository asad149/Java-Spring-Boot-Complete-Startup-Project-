package com.springSecurityNew.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springSecurityNew.dao.AuditLogDao;
import com.springSecurityNew.dao.AuditLogDetailsDao;
import com.springSecurityNew.entity.AuditLog;
import com.springSecurityNew.entity.AuditLogDetails;

@Component
public class DbLoggingFilter extends OncePerRequestFilter {
	
	@Autowired
	private AuditLogDao auditLogDao;
	
	@Autowired
	private AuditLogDetailsDao auditLogDetailsDao;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
	        ServletInputStream inputStream = request.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,Charset.defaultCharset()));
			String line ; 
			StringBuffer sb = new StringBuffer();
			while((line=  bufferedReader.readLine())!= null) {
				sb.append(line);
			}
			System.out.println("resFrom222 :" + sb.toString());
			
	
			
			AuditLog auditLog= new AuditLog(request.getRemoteAddr(),new Date(),request.getHeader("Authorization"),new Date(),"responseHeaders","rrn","stan","response_code","response_desc");
			AuditLogDetails auditLogDetails= new AuditLogDetails(auditLog,sb.toString(),"responseBody");
		      System.out.println("AUDIT LOGGING =========>>>  "+auditLogDao.save(auditLog));
		      System.out.println("AUDIT LOG DETAILS LOGGING =========>>>  "+auditLogDetailsDao.save(auditLogDetails));
		      
			   filterChain.doFilter(request, response);
	}

}
