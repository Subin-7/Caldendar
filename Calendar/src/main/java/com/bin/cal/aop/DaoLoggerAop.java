package com.bin.cal.aop;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoLoggerAop {

	public void before(JoinPoint j) {
		Logger logger = LoggerFactory.getLogger(j.getTarget()+"");
		logger.warn("&&&&& 메소드 실행 전 &&&&&");
		Object[] objs = j.getArgs();
		
		if(objs != null) {
			logger.warn("&&&&& {} &&&&&",j.getSignature().getName());
			for (int i = 0; i < objs.length; i++) {
				logger.warn(i+"번째 arg : \t"+String.valueOf(objs[i]));
			}
			logger.warn("&&&&& {} &&&&&",j.getSignature().getName());
		}
		
	}
	
	public void afterReturung(JoinPoint j) {
		Logger logger = LoggerFactory.getLogger(j.getTarget()+"");
		logger.warn("종료 : \t {}", j.getSignature().getName());
	}
	
	public void afterThrowing(JoinPoint j, Exception exception) {
		Logger logger = LoggerFactory.getLogger(j.getTarget()+"");
		logger.warn("에러발생: \t {}",j.getSignature().getName());
		logger.warn("에러발생: \t {}",exception.getMessage());
	}
}
