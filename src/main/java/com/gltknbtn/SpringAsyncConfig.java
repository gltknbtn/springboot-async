package com.gltknbtn;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SpringAsyncConfig implements AsyncConfigurer {



    @Bean(name="threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(30);//ayni anda çalıştırılacak task sayısı
        executor.setMaxPoolSize(50);//kuyruk doluysa aynı anda çalıştırılacak task sayısının çıkacağı maksimum değer
        executor.setQueueCapacity(1000);//kuyruk kapasitesi
        executor.setThreadNamePrefix("khsTask-");
      //  executor.setAllowCoreThreadTimeOut(true);
        executor.initialize();
        return  executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
     
}