package com.gft.orders.business.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.gft.orders.business..*(..))")
    public void businessMethods() { }

    @Before("businessMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Iniciando método: {} con argumentos: {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "businessMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Método finalizado: {} con resultado: {}", joinPoint.getSignature().toShortString(), result);
    }

    @AfterThrowing(pointcut = "businessMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error("Excepción en método: {} con mensaje: {}", joinPoint.getSignature().toShortString(), ex.getMessage(), ex);
    }

    @After("businessMethods()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Método completado: {}", joinPoint.getSignature().toShortString());
    }
}
