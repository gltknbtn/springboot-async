package com.gltknbtn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class CustomAsyncExceptionHandler
  implements AsyncUncaughtExceptionHandler {
 
    @Override
    public void handleUncaughtException(
            Throwable throwable, Method method, Object... obj) {
  
        System.out.println("Exception message - " + throwable.getMessage());
        System.out.println("Method name - " + method.getName());
        for (Object param : obj) {
            try {
                System.out.println("Parameter value - " + new ObjectMapper().writeValueAsString(param));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
     
}