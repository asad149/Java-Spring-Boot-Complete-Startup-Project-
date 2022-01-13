package com.springSecurityNew.configuration;

import java.io.IOException;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import com.springSecurityNew.util.FilterRequestResponseUtils;
import com.springSecurityNew.util.FilterRequestResponseUtils.BufferedRequestWrapper;
import com.springSecurityNew.util.FilterRequestResponseUtils.BufferedResponseWrapper;


@Component
public class ApiLoggingFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(ApiLoggingFilter.class);

	@Autowired
	private FilterRequestResponseUtils filterRequestResponseUtils;

//	@Value("${base.path}")
//	private String basePath;
	
//	@Value("${bpmserver.ip}")
//	private String bpmIp;
	
//	@Value("${bpmserver.sockettimeout}")
//	private int socketTimeout;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

//		HttpServletRequest request = (HttpServletRequest) ((ServletRequestWrapper) RequestContextHolder.getRequestAttributes())).getRequest();
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		logger.info("Remote Address ----------------> : {}" , httpServletRequest.getRemoteAddr());
		logger.info("X-FORWARDED-FOR ----------------> : {}" , httpServletRequest.getHeader("X-Forwarded-For"));
		logger.info("X-FORWARDED-FOR ----------------> : {}" , httpServletRequest.getHeader("X-FORWARDED-FOR"));

		HttpServletResponse httpServletResponse = (HttpServletResponse) response;


		try {

			BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(httpServletRequest);
			logger.info(bufferedRequest.getHeaderNames().toString());

			BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(httpServletResponse);
			String reqBody = bufferedRequest.getRequestBody();

			logger.info("reqBody :{}", reqBody);
			logger.info("method :{}", httpServletRequest.getMethod());

			saveReqToDb(httpServletRequest, bufferedRequest);

//			final StringBuilder logRequest = new StringBuilder("HTTP ").append(httpServletRequest.getMethod())
//					.append(" \"").append(httpServletRequest.getServletPath()).append("\" ").append(", parameters=")
//					.append(", body=").append(bufferedRequest.getRequestBody()).append(", remote_address=")
//					.append(httpServletRequest.getRemoteAddr());
//			logger.info(logRequest.toString());

			try {
				chain.doFilter(bufferedRequest, bufferedResponse);
			}
//			catch(Throwable ex){
//				logger.error(ex.toString());
//				logger.error(ex.getMessage());
//			}
			finally {
				final StringBuilder logResponse = new StringBuilder("HTTP RESPONSE ")
						.append(bufferedResponse.getContent());
				logger.info(logResponse.toString());

//				saveResToDb(bufferedResponse, httpServletResponse, 
//						activityLogsRepo, );
				MDC.clear();
			}

		} catch (Throwable e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		}

	}

	public void saveReqToDb(HttpServletRequest httpServletRequest, BufferedRequestWrapper bufferedRequest)
			throws IOException {

//		String path = httpServletRequest.getServletPath().substring(basePath.length());

//		String[] str = path.split("/", 4);
//		String controllerString = "/" + str[1] + "/" + str[2];

		String reqBody = bufferedRequest.getRequestBody();
//		String reqHeaders = filterRequestResponseUtils.getReqHeaders(httpServletRequest);
//		logger.info("reqHeaders: {}", reqHeaders);
		
		//call userDetailsFunction
//		String userDetails = UtilMethods.getIPAddress();
//		String ipHeaders = UtilMethods.getRequestIP(httpServletRequest);
//		logger.info("userDetails -------------------->>>>>>>>>>: {}", userDetails);
//		logger.info("IP_HEADERS --------------------- >>>>>>>>>>>>: {}", ipHeaders);

		String userId = null;
//		if (controllerString.equals("/user/login")) {
//			userId = filterRequestResponseUtils.getUserIdFromBody(reqBody);
//		} else {
//			userId = bufferedRequest.getHeader("UserId");
//		}

		String ipAddress = bufferedRequest.getHeader("IP-Address");

//		if (ipAddress == null || ipAddress.equals("null") || ipAddress.equals("")) {
//			activityLogs.setIpAddress(httpServletRequest.getRemoteAddr());
//		} else {
//			logger.info("getIPAddress: {}", ipAddress);
//			activityLogs.setIpAddress(ipAddress);
//		}
		
		
//		activityLogs.setUserId(userId);
//		activityLogs.setRequestBody(reqBody);
//		activityLogs.setRequestDate(new Date());
//		activityLogs.setRequestHeaders(reqHeaders);
//		activityLogs.setActivity(controllerString);
//		activityLogs.setUserDetails(userDetails);

	}

	public void saveResToDb(BufferedResponseWrapper bufferedResponse, HttpServletResponse httpServletResponse) throws IOException {

		Integer status = (Integer) httpServletResponse.getStatus();
		String responseBody = bufferedResponse.getContent();
//		String responseHeaders = filterRequestResponseUtils.getResHeaders(httpServletResponse);

//		activityLogs.setResponseHeaders(responseHeaders);
//		activityLogs.setResponseCode(status.toString());
//
//		activityLogs.setResponseBody(responseBody);
//		activityLogs.setResponseDate(new Date());

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
