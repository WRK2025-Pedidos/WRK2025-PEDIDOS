package com.gft.orders.unittest.exceptions;

import com.gft.orders.business.config.exceptions.BusinessExceptions;
import com.gft.orders.presentation.config.ErrorResponse;
import com.gft.orders.presentation.config.ExceptionsGeneralHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExceptionsGeneralHandlerTest {

    private ExceptionsGeneralHandler handler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        handler = new ExceptionsGeneralHandler();
        webRequest = mock(WebRequest.class);
    }

    @Test
    void handleAllExceptions_ShouldReturnInternalServerError_Test() {
        Exception ex = new Exception("Test exception");
        ResponseEntity<Object> response = handler.handleAllExceptions(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Something went wrong", ((ErrorResponse)response.getBody()).getError());
    }

    @Test
    void handleBusinessExceptions_ShouldReturnInternalServerError_Test() {
        BusinessExceptions ex = new BusinessExceptions("Business error");
        ResponseEntity<Object> response = handler.handleBusinessExceptions(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Business error", ((ErrorResponse)response.getBody()).getError());
    }

    @Test
    void handleHttpMessageNotReadable_ShouldReturnBadRequest_Test() throws Exception {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Invalid JSON");
        ResponseEntity<Object> response = handler.handleHttpMessageNotReadable(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid JSON", response.getBody());
    }

    @Test
    void handleMethodArgumentNotValid_ShouldReturnBadRequest_Test() throws Exception {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unable to validate request parameters", ((ErrorResponse)response.getBody()).getError());
    }

    @Test
    void handleTypeMismatch_ShouldReturnBadRequestWithDetails_Test() throws Exception {
        TypeMismatchException ex = new TypeMismatchException("abc", Integer.class);

        try {
            var field = TypeMismatchException.class.getDeclaredField("propertyName");
            field.setAccessible(true);
            field.set(ex, "quantity");
        } catch (Exception e) {
            fail("Failed to set propertyName via reflection");
        }

        ResponseEntity<Object> response = handler.handleTypeMismatch(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((ErrorResponse)response.getBody()).getError().contains("quantity"));
        assertTrue(((ErrorResponse)response.getBody()).getError().contains("abc"));
        assertTrue(((ErrorResponse)response.getBody()).getError().contains("Integer"));
    }

    @Test
    void handleTypeMismatch_WithNullRequiredType_ShouldHandleGracefully_Test() throws Exception {
        TypeMismatchException ex = new TypeMismatchException("abc", null);

        try {
            var field = TypeMismatchException.class.getDeclaredField("propertyName");
            field.setAccessible(true);
            field.set(ex, "price");
        } catch (Exception e) {
            fail("Failed to set propertyName via reflection");
        }

        ResponseEntity<Object> response = handler.handleTypeMismatch(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((ErrorResponse)response.getBody()).getError().contains("unknown type"));
    }
}