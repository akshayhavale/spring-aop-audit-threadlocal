package com.app.configuration;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class ThreadLocalUtils {

	public static void setThreadLocalValue(Object value) {
		RequestContextHolder.getRequestAttributes().setAttribute("myThreadLocal", value,
				RequestAttributes.SCOPE_REQUEST);
	}

	public static Object getThreadLocalValue() {
		return RequestContextHolder.getRequestAttributes().getAttribute("myThreadLocal",
				RequestAttributes.SCOPE_REQUEST);
	}

	public static void clearThreadLocalValue() {
		RequestContextHolder.getRequestAttributes().removeAttribute("myThreadLocal", RequestAttributes.SCOPE_REQUEST);
	}
}
