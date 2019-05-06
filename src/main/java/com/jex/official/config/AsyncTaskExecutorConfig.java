package com.jex.official.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncTaskExecutorConfig implements AsyncConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(AsyncTaskExecutorConfig.class);
    @Override
    public Executor getAsyncExecutor() {
        return getTaskExecutor();
    }

    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            logger.error("======================"+method.getName()+"================");
            logger.error(throwable.getMessage(), throwable);
            logger.error("======================"+method.getName()+"================");
        };
    }

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor getTaskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2); //如果池中的实际线程数小于corePoolSize,无论是否其中有空闲的线程，都会给新的任务产生新的线程
        taskExecutor.setMaxPoolSize(25); //连接池中保留的最大连接数。
        taskExecutor.setKeepAliveSeconds(300);
        taskExecutor.setThreadNamePrefix("official_");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
