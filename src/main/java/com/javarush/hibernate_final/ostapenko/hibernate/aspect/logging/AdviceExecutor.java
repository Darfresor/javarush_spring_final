package com.javarush.hibernate_final.ostapenko.hibernate.aspect.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(1)
public class AdviceExecutor {
    @Before("PointCutSearcher.homeAndProfileControllers()")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        Logger log = LoggerFactory.getLogger(className);
        log.info("Начало работы метода: {}", joinPoint.getSignature().getName());
    }
    @After("PointCutSearcher.homeAndProfileControllers()")
    public void logAfter(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        Logger log = LoggerFactory.getLogger(className);
        log.info("Завершение работы метода: {}", joinPoint.getSignature().getName());

        //информация для режима debug
        if (log.isDebugEnabled()) {
            Object[] args = joinPoint.getArgs();
            log.debug("Параметры метода: {}", Arrays.toString(args));
        }

    }
}
