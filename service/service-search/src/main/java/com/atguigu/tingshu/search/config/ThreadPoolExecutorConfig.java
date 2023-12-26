package com.atguigu.tingshu.search.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolExecutorConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        //  创建线程池--自定义线程池.
        return new ThreadPoolExecutor(
                8,
                16,
                3,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10)
        );
    }
}
