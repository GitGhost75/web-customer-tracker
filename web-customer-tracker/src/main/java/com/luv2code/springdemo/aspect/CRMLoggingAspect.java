package com.luv2code.springdemo.aspect;

import java.util.Arrays;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());

	// setup pointcut declarations
	// controller package
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	public void controllerPackage() {
	}

	// dao package
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	public void daoPackage() {
	}

	// service package
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	public void servicePackage() {
	}

	// put it all together
	@Pointcut("controllerPackage() || daoPackage() || servicePackage()")
	public void forAppFlow() {
	}

	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {

		// display method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("=====>> in @Before: calling method: " + theMethod);

		// display arguments of method
		Object[] theArguments = theJoinPoint.getArgs();

		if (null != theArguments) {
			Arrays.asList().stream().forEach(arg -> {
				myLogger.info("=====>> Argument: " + arg);
			});
		}
	}

	@AfterReturning(pointcut = ("forAppFlow()"), returning = "theResult")
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {

		// display method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("=====>> in @AfterReturning: calling method: " + theMethod);

		// display result
		myLogger.info("=====>> result: " + theResult);
	}
}
