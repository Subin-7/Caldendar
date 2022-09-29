package com.bin.cal.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("========= Access Logger Filter 작동 =========");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 요청정보
		HttpServletRequest req = (HttpServletRequest)request;
		
		String url = StringUtils.defaultIfEmpty(req.getRequestURL().toString(), "-");
		String queryString = StringUtils.defaultString(req.getQueryString(), "");
		String remoteAddr = StringUtils.defaultIfEmpty(req.getRemoteAddr(), "-");
		
		String userAgent = StringUtils.defaultIfEmpty(req.getHeader("User-Agent"), "-");
		String referer = StringUtils.defaultIfEmpty(req.getHeader("Referer"), "-");
		
		String log = String.format("%s?%s : %s \n %s %s\n", url, queryString, remoteAddr, userAgent,referer );
		
		logger.info(log);

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		logger.info("========= Access Logger Filter 종료 =========");
	}

}
