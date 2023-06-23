package com.app.configuration;

import static java.util.Objects.nonNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.app.controller.user.UserLogin;
import com.app.custom.annatations.AuditV;
import com.app.entity.user.User;
import com.app.repository.userlogin.UserLoginRespository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
@Order(value = 0)
public class AuditHandler {

	private final UserLoginRespository userLoginRespository;

	public AuditHandler(UserLoginRespository userLoginRespository) {
		this.userLoginRespository = userLoginRespository;
	}

	@Before("execution(* com.app.controller.*.*.*(..))"
			+ "&& @annotation(com.app.custom.annatations.EnableAuditing)"
			+ "&& @target(com.app.custom.annatations.EnableAuditing)")
	public void auditRequestBeforeController(JoinPoint joinPoint) {
		System.out.println("Entered Before Method");

		// clear ThreadLocal approach1
		// ThreaLocalDto.clearThreadLocal();

		// approach2

		ThreadLocalUtils.clearThreadLocalValue();

	}

	@After("execution(* com.app.controller.*.*.*(..))"
			+ " && @annotation(com.app.custom.annatations.EnableAuditing)"
			+ " && @target(com.app.custom.annatations.EnableAuditing)")
	public void auditResponseAfterController(JoinPoint joinpoint) {

		System.out.println("Entered After Method");
		// Approach 1
//		ThreadLocal<String> threadLocal = ThreaLocalDto.getThreadLocal();
//
//		String info = threadLocal.get();

		// Approach 2

		Object threadLocalValue = ThreadLocalUtils.getThreadLocalValue();
		this.saveUserLoginInfo(threadLocalValue);

	}
	
	@AfterThrowing(pointcut = "execution(* com.app.controller.*.*.*(..))"
			+ " && @annotation(com.app.custom.annatations.EnableAuditing)"
			+ " && @target(com.app.custom.annatations.EnableAuditing)",throwing = "exception")
	public void handleExceptionCase(Exception exception) {
		System.out.println("Exception case after throwing enetered");
		String message = exception.getMessage();
		this.saveUserLoginInfo(message);
		
	}

	@Async
	private void saveUserLoginInfo(Object info) {

		String loginInfo = null;

		if (info instanceof User) {

			try {
				try {
					loginInfo = "User information : " + getUserLoginInfoFromObject(info);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		} else if (info instanceof String) {
			loginInfo = info.toString();
		}

		UserLogin userLogin = new UserLogin();
		if (nonNull(loginInfo)) {
			userLogin.setInfo(loginInfo);
			userLoginRespository.save(userLogin);

		}
	}

	private String getUserLoginInfoFromObject(Object object) throws IllegalArgumentException, IllegalAccessException, JsonProcessingException {
		String info = "";
		ObjectMapper mapper = new ObjectMapper();
		if (object instanceof User) {
			Class<User> userClass = User.class;
			Field[] fields = userClass.getDeclaredFields();
			Map<Object, Object> map = new HashMap<>();
			for (Field field : fields) {

				if (field.isAnnotationPresent(AuditV.class)) {

					field.setAccessible(true);

					Object value = field.get((User) object);

					Object key = field.getName();

					map.put(key, value);

				}

			}

			info = mapper.writeValueAsString(map);

		}
		return info;

	}

}
