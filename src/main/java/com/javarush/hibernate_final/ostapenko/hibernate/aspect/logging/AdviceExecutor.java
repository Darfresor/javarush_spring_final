package com.javarush.hibernate_final.ostapenko.hibernate.aspect.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class AdviceExecutor {
    @Before("com.javarush.hibernate_final.ostapenko.hibernate.aspect.logging.PointCutSearcher.publicMethodsInHomePackage()")
    public void logBeforeHomeService(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        Logger log = LoggerFactory.getLogger(className);
        log.info("Начало работы метода: {}", joinPoint.getSignature().getName());
    }
}
