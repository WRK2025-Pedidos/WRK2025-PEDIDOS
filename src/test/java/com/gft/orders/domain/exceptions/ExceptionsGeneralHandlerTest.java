package com.gft.orders.domain.exceptions;

import org.hibernate.TypeMismatchException;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ControllerAdvice
public class ExceptionsGeneralHandlerTest {

    ExceptionsGeneralHandlerTest handler = new ExceptionsGeneralHandlerTest();

    @Test
    void handleGenericException_ShouldReturnInternalServerError() {
        Exception ex = new Exception("Unexpected error");
        ResponseEntity<Object> response = handler.handleAllExceptions(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(((ErrorResponse) response.getBody()).getError().contains("Something went wrong"));
    }


    @Test
    void handleBusinessException_ShouldReturnInternalServerError() {
        BusinessExceptions ex = new BusinessExceptions("Domain error");
        ResponseEntity<Object> response = handler.handleBusinessExceptions(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Domain error", ((ErrorResponse) response.getBody()).getError());
    }

    @Test
    void handleHttpMessageNotReadable_ShouldReturnBadRequest() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Malformed JSON", (HttpInputMessage) null);
        WebRequest request = mock(WebRequest.class);
        ResponseEntity<Object> response = handler.handleHttpMessageNotReadable(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Malformed JSON", response.getBody());
    }

    @Test
    void handleMethodArgumentNotValid_ShouldReturnBadRequest() {
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "object");
        MethodParameter param = mock(MethodParameter.class);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(param, bindingResult);
        WebRequest request = mock(WebRequest.class);
        ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((ErrorResponse) response.getBody()).getError().contains("Unable to validate request parameters"));
    }

    @Test
    void handleTypeMismatch_ShouldReturnBadRequest() {
        TypeMismatchException ex = new TypeMismatchException("abc", Integer.class);
        WebRequest request = mock(WebRequest.class);
        ex.initPropertyName("quantity");
        ResponseEntity<Object> response = handler.handleTypeMismatch(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((ErrorResponse) response.getBody()).getError().contains("Invalid type"));
    }

    @Test
    void handleTypeMismatch_ShouldThrowNullPointerException_WhenRequiredTypeIsNull() {
        TypeMismatchException ex = mock(TypeMismatchException.class);
        when(ex.getPropertyName()).thenReturn("price");
        when(ex.getValue()).thenReturn("abc");
        when(ex.getRequiredType()).thenReturn(null);

        WebRequest request = mock(WebRequest.class);

        assertThrows(NullPointerException.class, () ->
                handler.handleTypeMismatch(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request)
        );
    }

}
