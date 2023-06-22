package com.app.configuration;

public class ThreaLocalDto {

	private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

	public static ThreadLocal<String> getThreadLocal() {
		return threadLocal;
	}

	public static void clearThreadLocal() {
		threadLocal.remove();
	}

}
