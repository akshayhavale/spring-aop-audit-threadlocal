package com.app.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.app.controller.user.UserLogin;
import com.app.repository.userlogin.UserLoginRespository;

@Aspect
@Component
@Order(value = 0)
public class AuditHandler {

	private final UserLoginRespository userLoginRespository;

	public AuditHandler(UserLoginRespository userLoginRespository) {
		this.userLoginRespository = userLoginRespository;
	}

	@Before("execution(* com.app.controller.*.*.*(..))")
	public void auditRequestBeforeController(JoinPoint joinPoint) {
		System.out.println("Entered Before Method");

		// clear ThreadLocal approach1
	//	ThreaLocalDto.clearThreadLocal();
		
		//approach2
		
		ThreadLocalUtils.clearThreadLocalValue();

	}

	@After("execution(* com.app.controller.*.*.*(..))")
	public void auditResponseAfterController(JoinPoint joinpoint) {

		System.out.println("Entered After Method");
		//Approach 1
//		ThreadLocal<String> threadLocal = ThreaLocalDto.getThreadLocal();
//
//		String info = threadLocal.get();

		//Approach 2
		
		Object threadLocalValue = ThreadLocalUtils.getThreadLocalValue();
		this.saveUserLoginInfo(threadLocalValue.toString());

	}

	@Async
	private void saveUserLoginInfo(String info) {

		UserLogin userLogin = new UserLogin();
		userLogin.setInfo(info);

		userLoginRespository.save(userLogin);

	}

}
