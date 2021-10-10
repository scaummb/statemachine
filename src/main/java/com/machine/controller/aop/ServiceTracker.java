package com.machine.controller.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author moubin.mo
 * @date: 2021/10/10 15:06
 */
@Aspect
@Component
public class ServiceTracker {

	private final static Logger logger = LoggerFactory.getLogger(ServiceTracker.class);

	// 任务切点
	@Pointcut("execution(public * com.machine..*(..)) && @annotation(com.machine.controller.aop.Tracker)")
	public void servicePoint() {
	}

	@Around("servicePoint()")
	public void taskPoint(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("joinPoint = {}", joinPoint);
	}

}
