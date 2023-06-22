package com.app.configuration;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer {

	@Override
	@Bean(name = "threadPoolTaskExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10); // Set the initial number of threads in the pool
		executor.setMaxPoolSize(100); // Set the maximum number of threads in the pool
		executor.setQueueCapacity(1000); // Set the maximum number of tasks that can be queued
		executor.setThreadNamePrefix("AsyncThread-"); // Set a prefix for the thread names
		executor.initialize();
		return executor;
	}
}
