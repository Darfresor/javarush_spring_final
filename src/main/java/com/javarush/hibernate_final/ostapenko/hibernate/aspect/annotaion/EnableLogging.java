package com.javarush.hibernate_final.ostapenko.hibernate.aspect.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableLogging {
    /**
     * Пользовательское описание операции (опционально).
     */
    String description() default "";
    /**
     * Логировать ли входные параметры.
     */
    boolean logParams() default true;
    /**
     * Логировать ли результат выполнения.
     */
    boolean logResult() default true;
}
