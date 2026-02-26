package com.javarush.hibernate_final.ostapenko.hibernate.aspect.logging;

import com.javarush.hibernate_final.ostapenko.hibernate.aspect.annotaion.EnableLogging;
import com.javarush.hibernate_final.ostapenko.hibernate.config.LoggingProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Aspect
@Component
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingAspect {
    private static final String TRACE_ID_KEY = "traceId";
    private final LoggingProperties loggingProperties;

    public LoggingAspect(LoggingProperties loggingProperties) {
        this.loggingProperties = loggingProperties;
    }

    // Определяем срез (pointcut) для всех методов, помеченных нашей аннотацией
    @Pointcut("@within(enableLogging) || @annotation(enableLogging)")
    public void methodsToLog(EnableLogging enableLogging) {}

    @Around(value = "methodsToLog(enableLogging)", argNames = "joinPoint,enableLogging")
    public Object logAround(ProceedingJoinPoint joinPoint, EnableLogging enableLogging) throws Throwable {

        // Если глобальное логирование выключено, просто выполняем метод
        if (!loggingProperties.isEnabled()) {
            return joinPoint.proceed();
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        String operationDescription = Optional.ofNullable(enableLogging)
                .map(EnableLogging::description)
                .orElse(className + "." + methodName);

        // Генерируем traceId для связки всех логов в рамках одного вызова
        String traceId = UUID.randomUUID().toString();
        MDC.put(TRACE_ID_KEY, traceId); // Помещаем в MDC, чтобы он автоматом добавлялся в каждый лог

        Logger log = LoggerFactory.getLogger(className);
        Object result = null;
        boolean isSuccess = true;
        long startTime = System.currentTimeMillis();

        try {
            // Логируем вход в метод
            if (enableLogging.logParams()) {
                Object[] args = joinPoint.getArgs();
                log.info("[Trace: {}] --> {} | Параметры: {}",
                        traceId, operationDescription, Arrays.toString(args));
            } else {
                log.info("[Trace: {}] --> {} | Параметры скрыты",
                        traceId, operationDescription);
            }

            result = joinPoint.proceed(); // Выполнение целевого метода
            return result;

        } catch (Throwable ex) {
            isSuccess = false;
            log.error("[Trace: {}] <-- {} | Исключение: {}",
                    traceId, operationDescription, ex.getMessage(), ex); // Важно передать сам ex для stacktrace
            throw ex; // Пробрасываем исключение дальше, не меняя поведение бизнес-логики

        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            // Логируем выход из метода
            if (isSuccess) {
                if (enableLogging.logResult()) {
                    log.info("[Trace: {}] <-- {} | Завершено за {} ms | Результат: {}",
                            traceId, operationDescription, executionTime, result);
                } else {
                    log.info("[Trace: {}] <-- {} | Завершено за {} ms | Результат скрыт",
                            traceId, operationDescription, executionTime);
                }
            }
            MDC.remove(TRACE_ID_KEY); // Очищаем MDC, чтобы traceId не попал в другие потоки/запросы
        }
    }
}
