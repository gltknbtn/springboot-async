package com.gltknbtn.aspect;

import com.gltknbtn.annotation.LogMethodPerformance;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class MethodPerfomanceAspect {

    @Around("@annotation(com.gltknbtn.annotation.LogMethodPerformance)")
    public Object logMethodPerformance(ProceedingJoinPoint joinPoint) throws Throwable{
        try {
            long tic = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();
            long toc = System.currentTimeMillis();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            LogMethodPerformance myAnnotation = method.getAnnotation(LogMethodPerformance.class);
            String methodName = myAnnotation.name().isEmpty() ? joinPoint.getSignature().getName() : myAnnotation.name();

            System.out.println("=============================");
            System.out.println(methodName + " executed in " + (toc-tic) + " ms.");
            System.out.println("Method parameters: "+ joinPoint.getArgs());
            System.out.println("=============================");
            return proceed;

        }catch (Exception e){
            System.out.println("Exception occured in aspect");
            throw  e;
        }

    }
}
