package org.example.searchservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class MethodPerformanceAspect {

    private static final long SLOW_QUERY_THRESHOLD = 300;

    private static final Logger logger = LoggerFactory.getLogger(MethodPerformanceAspect.class);

    @Around("@annotation(org.example.searchservice.domain.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        var startTime = System.currentTimeMillis();
        var result = joinPoint.proceed();
        var duration = System.currentTimeMillis() - startTime;
        var name = joinPoint.getSignature().getName();
        var message = String.format("[%s] Method %s took %d ms", UUID.randomUUID(), name, duration);
        var isMethodSlow = duration > SLOW_QUERY_THRESHOLD;
        if (isMethodSlow) logger.warn(message);
        else  logger.info(message);
        return result;
    }

}
