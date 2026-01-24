package com.javarush.hibernate_final.ostapenko.hibernate.aspect.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointCutSearcher {
    @Pointcut("execution(public * *(..))") // любой публичный метод
    public void publicMethods() {}

    @Pointcut("within(com.javarush.hibernate_final.ostapenko.hibernate.controller.home..*)")
    public void homePackageControllers() {}

    //  Комбинированный pointcut
    @Pointcut("publicMethods() && homePackageControllers()")
    public void publicMethodsInHomePackage() {}
}
