package com.backend.todo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author Alexey Voronin.
 * @since 18.01.2023.
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("com.backend.todo.aop.CommonPointcuts.isServiceLayer()")
    public void loggingBeforeServiceLayer(JoinPoint joinPoint) {
        log.info("Incoming params - {}: args - {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(value = "com.backend.todo.aop.CommonPointcuts.isServiceLayer() ", returning = "result")
    public void loggingAfterReturningServiceLayer(JoinPoint joinPoint, Object result) {
        log.info("Returning value - {}: result - {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "com.backend.todo.aop.CommonPointcuts.isServiceLayer()", throwing = "ex")
    public void loggingAfterThrowingServiceLayer(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception - {}: {}: {}", joinPoint.getSignature(), ex.getClass(), ex.getMessage());
    }

    @AfterThrowing(value = "com.backend.todo.aop.CommonPointcuts.isControllerLayer()", throwing = "ex")
    public void loggingAfterThrowingControllerLayer(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception - {}: {}: {}", joinPoint.getSignature(), ex.getClass(), ex.getMessage());
    }
}
