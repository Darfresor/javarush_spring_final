package com.javarush.hibernate_final.ostapenko.hibernate.aspect.logging;

import com.javarush.hibernate_final.ostapenko.hibernate.aspect.annotaion.EnableLogging;
import com.javarush.hibernate_final.ostapenko.hibernate.config.LoggingProperties;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointCutSearcher {

    //срез включает в себя определенный пакет(ы)
    @Pointcut("within(com.javarush.hibernate_final.ostapenko.hibernate.controller.home..*)")
    public void homePackageControllers() {}

    //срез включает в себя определенный пакет(ы)
    @Pointcut("within(com.javarush.hibernate_final.ostapenko.hibernate.controller.profile..*)")
    public void profilePackageControllers() {}

    //  Комбинированный pointcut для двух пакетов контроллеров
    @Pointcut("homePackageControllers() || profilePackageControllers()")
    public void homeAndProfileControllers() {}



}
