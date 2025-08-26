package com.pragma.crediya.api.middleware;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

     @Pointcut("execution(* com.pragma.crediya.api..*(..))")
    public void monitoredMethods() {}

    @Around("monitoredMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        LocalDateTime startTime = LocalDateTime.now();

        log.info("-> [{}] Inicia {} a las {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getSignature().getDeclaringTypeName(),
                startTime);
                // Arrays.toString(joinPoint.getArgs()));

        try {
            Object result = joinPoint.proceed();

            long end = System.currentTimeMillis();
            // LocalDateTime endTime = LocalDateTime.now();

            // log.info("--> [{}] Finalizó {} a las {}",
            //         joinPoint.getSignature().toShortString(),
            //         joinPoint.getSignature().getDeclaringTypeName(),
            //         endTime);

            return result;
        } catch (Throwable ex) {
            log.error("-> [{}] Excepción en {}: {}",
                    joinPoint.getSignature().toShortString(),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    ex.getMessage());
            throw ex;
        }
    }
}
