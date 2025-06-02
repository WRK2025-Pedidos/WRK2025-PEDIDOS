package com.gft.orders.unittest.business.config;

import com.gft.orders.business.config.LogAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LogAspectTest {

    @Mock
    private Logger logger;

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private Signature signature;

    private LogAspect logAspect;

    @BeforeEach
    void setUp() {
        logAspect = new LogAspect();
        ReflectionTestUtils.setField(logAspect, "logger", logger);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.toShortString()).thenReturn("public void createOrder(..)");
        when(joinPoint.getArgs()).thenReturn(new Object[0]);
    }

    @Test
    void shouldLogBeforeMethod() {
        logAspect.logBefore(joinPoint);

        verify(logger).info("Iniciando método: {} con argumentos: {}", "public void createOrder(..)", new Object[0]);
    }

    @Test
    void shouldLogBeforeMethodWithArguments() {
        Object[] arguments = {"arg1", 2};

        when(joinPoint.getArgs()).thenReturn(arguments);  // Se pasan argumentos

        logAspect.logBefore(joinPoint);

        verify(logger).info("Iniciando método: {} con argumentos: {}", "public void createOrder(..)", arguments);
    }

    @Test
    void shouldLogAfterReturningMethod() {
        logAspect.logAfterReturning(joinPoint, "Success");

        verify(logger).info("Método finalizado: {} con resultado: {}", "public void createOrder(..)", "Success");
    }

    @Test
    void shouldLogAfterThrowingMethod() {
        Throwable exception = new Exception("Error");

        logAspect.logAfterThrowing(joinPoint, exception);

        verify(logger).error("Excepción en método: {} con mensaje: {}", "public void createOrder(..)", "Error", exception);
    }

    @Test
    void shouldLogAfterMethod() {
        logAspect.logAfter(joinPoint);

        verify(logger).info("Método completado: {}", "public void createOrder(..)");
    }
}
