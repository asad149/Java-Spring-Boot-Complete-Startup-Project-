package com.springSecurityNew.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.TeeOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FilterRequestResponseUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(FilterRequestResponseUtils.class);
	
	
//	public String getReqHeaders(HttpServletRequest req) throws IOException {
//		String headersList = "";
//
//		Map<String, String> headers = Collections.list(req.getHeaderNames()).stream()
//				.collect(Collectors.toMap(h -> h, req::getHeader));
//
//		for (Map.Entry<String, String> entry : headers.entrySet()) {
//			logger.info("{} : {} , ",entry.getKey(),entry.getValue());
//			headersList += entry.getKey() + ":" + entry.getValue() + ",";
//		}
//		headersList=org.apache.commons.lang3.StringUtils.chop(headersList);
//		return headersList;
//	}
//	
	
//	public String getResHeaders(HttpServletResponse res) throws IOException {
//		String headersList = "";
//
//	     ArrayList<String> names = new ArrayList<>(res.getHeaderNames());
//	     
//			Map<String, String> headers = new HashMap<>();
//			names.forEach(name -> {
//				if (res.getHeader(name) != null) {
//					headers.put(name, res.getHeader(name));
//				}
//			});
//	     
//			for (Map.Entry<String, String> entry : headers.entrySet()) {
//				logger.info("{} : {} , ",entry.getKey(),entry.getValue());
//				headersList += entry.getKey() + ":" + entry.getValue() + ",";
//			}
//			
//		headersList=org.apache.commons.lang3.StringUtils.chop(headersList);
//		return headersList;
//	}
	
	

	
	
//	public Boolean isIpAllowed(String clientIp) {
//		Boolean allow = false;
//		if(allowedIps.equals("*"))
//			return true;			
//		List<String> allowedIpsList = Arrays.asList(allowedIps.split(","));				
//		allow = allowedIpsList.contains(clientIp);
//		return allow;
//		
//	}

	
//	/ for getting request parameters safely /
	public Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {
		Map<String, String> typesafeRequestMap = new HashMap<String, String>();
		Enumeration<?> requestParamNames = request.getParameterNames();
		while (requestParamNames.hasMoreElements()) {
			String requestParamName = (String) requestParamNames.nextElement();
			String requestParamValue;
			if (requestParamName.equalsIgnoreCase("password")) {
				requestParamValue = "********";
			} else {
				requestParamValue = request.getParameter(requestParamName);
			}
			typesafeRequestMap.put(requestParamName, requestParamValue);
		}
		return typesafeRequestMap;
	}

//	/ for creating a customer request wrapper /
	public static class BufferedRequestWrapper extends HttpServletRequestWrapper {
		private ByteArrayInputStream bais = null;
		private ByteArrayOutputStream baos = null;
		private BufferedServletInputStream bsis = null;
		private byte[] buffer = null;

		public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
			super(req);
			// Read InputStream and store its content in a buffer.
			InputStream is = req.getInputStream();
			this.baos = new ByteArrayOutputStream();
			byte buf[] = new byte[1024];
			int read;
			while ((read = is.read(buf)) > 0) {
				this.baos.write(buf, 0, read);
			}
			this.buffer = this.baos.toByteArray();
		}

		@Override
		public ServletInputStream getInputStream() {
			this.bais = new ByteArrayInputStream(this.buffer);
			this.bsis = new BufferedServletInputStream(this.bais);
			return this.bsis;
		}

		public String getRequestBody() throws IOException {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
			String line = null;
			StringBuilder inputBuffer = new StringBuilder();
			do {
				line = reader.readLine();
				if (null != line) {
					inputBuffer.append(line.trim());
				}
			} while (line != null);
			reader.close();
			return inputBuffer.toString().trim();
		}
	}

	public static final class BufferedServletInputStream extends ServletInputStream {
		private ByteArrayInputStream bais;

		public BufferedServletInputStream(ByteArrayInputStream bais) {
			this.bais = bais;
		}

		@Override
		public int available() {
			return this.bais.available();
		}

		@Override
		public int read() {
			return this.bais.read();
		}

		@Override
		public int read(byte[] buf, int off, int len) {
			return this.bais.read(buf, off, len);
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setReadListener(ReadListener readListener) {

		}
	}

	public static class TeeServletOutputStream extends ServletOutputStream {
		private final TeeOutputStream targetStream;

		public TeeServletOutputStream(OutputStream one, OutputStream two) {
			targetStream = new TeeOutputStream(one, two);
		}

		@Override
		public void write(int arg0) throws IOException {
			this.targetStream.write(arg0);
		}

		public void flush() throws IOException {
			super.flush();
			this.targetStream.flush();
		}

		public void close() throws IOException {
			super.close();
			this.targetStream.close();
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {

		}
	}

//	/ for creating a customer response wrapper /
	public static class BufferedResponseWrapper implements HttpServletResponse {
		HttpServletResponse original;
		TeeServletOutputStream tee;
		ByteArrayOutputStream bos;

		public BufferedResponseWrapper(HttpServletResponse response) {
			original = response;
		}

		public String getContent() {
			if(bos == null) {
				return "";
			}
			return bos.toString();
		}

		public PrintWriter getWriter() throws IOException {
			return original.getWriter();
		}

		public ServletOutputStream getOutputStream() throws IOException {
			if (tee == null) {
				bos = new ByteArrayOutputStream();
				tee = new TeeServletOutputStream(original.getOutputStream(), bos);
			}
			return tee;

		}

		@Override
		public String getCharacterEncoding() {
			return original.getCharacterEncoding();
		}

		@Override
		public String getContentType() {
			return original.getContentType();
		}

		@Override
		public void setCharacterEncoding(String charset) {
			original.setCharacterEncoding(charset);
		}

		@Override
		public void setContentLength(int len) {
			original.setContentLength(len);
		}

		@Override
		public void setContentLengthLong(long l) {
			original.setContentLengthLong(l);
		}

		@Override
		public void setContentType(String type) {
			original.setContentType(type);
		}

		@Override
		public void setBufferSize(int size) {
			original.setBufferSize(size);
		}

		@Override
		public int getBufferSize() {
			return original.getBufferSize();
		}

		@Override
		public void flushBuffer() throws IOException {
			tee.flush();
		}

		@Override
		public void resetBuffer() {
			original.resetBuffer();
		}

		@Override
		public boolean isCommitted() {
			return original.isCommitted();
		}

		@Override
		public void reset() {
			original.reset();
		}

		@Override
		public void setLocale(Locale loc) {
			original.setLocale(loc);
		}

		@Override
		public Locale getLocale() {
			return original.getLocale();
		}

		@Override
		public void addCookie(Cookie cookie) {
			original.addCookie(cookie);
		}

		@Override
		public boolean containsHeader(String name) {
			return original.containsHeader(name);
		}

		@Override
		public String encodeURL(String url) {
			return original.encodeURL(url);
		}

		@Override
		public String encodeRedirectURL(String url) {
			return original.encodeRedirectURL(url);
		}

		@SuppressWarnings("deprecation")
		@Override
		public String encodeUrl(String url) {
			return original.encodeUrl(url);
		}

		@SuppressWarnings("deprecation")
		@Override
		public String encodeRedirectUrl(String url) {
			return original.encodeRedirectUrl(url);
		}

		@Override
		public void sendError(int sc, String msg) throws IOException {
			original.sendError(sc, msg);
		}

		@Override
		public void sendError(int sc) throws IOException {
			original.sendError(sc);
		}

		@Override
		public void sendRedirect(String location) throws IOException {
			original.sendRedirect(location);
		}

		@Override
		public void setDateHeader(String name, long date) {
			original.setDateHeader(name, date);
		}

		@Override
		public void addDateHeader(String name, long date) {
			original.addDateHeader(name, date);
		}

		@Override
		public void setHeader(String name, String value) {
			original.setHeader(name, value);
		}

		@Override
		public void addHeader(String name, String value) {
			original.addHeader(name, value);
		}

		@Override
		public void setIntHeader(String name, int value) {
			original.setIntHeader(name, value);
		}

		@Override
		public void addIntHeader(String name, int value) {
			original.addIntHeader(name, value);
		}

		@Override
		public void setStatus(int sc) {
			original.setStatus(sc);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void setStatus(int sc, String sm) {
			original.setStatus(sc, sm);
		}

		@Override
		public String getHeader(String arg0) {
			return original.getHeader(arg0);
		}

		@Override
		public Collection<String> getHeaderNames() {
			return original.getHeaderNames();
		}

		@Override
		public Collection<String> getHeaders(String arg0) {
			return original.getHeaders(arg0);
		}

		@Override
		public int getStatus() {
			return original.getStatus();
		}
	}
}
