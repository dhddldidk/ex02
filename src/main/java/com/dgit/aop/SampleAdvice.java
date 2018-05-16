package com.dgit.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dgit.controller.ReplyController;

@Aspect
@Component
public class SampleAdvice {

	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	//addMessage 서비스가 호출되기 전에 이 함수가 호출되게 할꺼임
	@Before("execution(* com.dgit.service.MessageServiceImpl.addMessage(..))")
	public void startLog(JoinPoint jp){
		logger.info("==================================================");
		logger.info("[startLog]");
		logger.info("==================================================");

	}
	
	@Around("execution(* com.dgit.service.MessageServiceImpl.readMessage(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable{
		//readMessage 동작하는 시간 재보기
		long startTime = System.currentTimeMillis();
		logger.info("==================================================");
		logger.info("[timeLog] START");
		logger.info("==================================================");
		
		Object result = pjp.proceed();//proceed가 readMessage를 받음
		
		long endTime = System.currentTimeMillis();
		logger.info("==================================================");
		logger.info("[timeLog] END, time : "+(endTime-startTime));
		logger.info("==================================================");
		
		return result;
	}
}
