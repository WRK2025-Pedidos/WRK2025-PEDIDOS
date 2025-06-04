package com.gft.orders.presentation.config;

import com.gft.orders.business.config.exceptions.BusinessExceptions;
import com.gft.orders.business.config.exceptions.InvalidReturnQuantityException;
import com.gft.orders.business.config.exceptions.OrderNotFoundException;
import com.gft.orders.business.config.exceptions.ReturnPeriodExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "com.gft.orders.presentation.controllers")
public class ExceptionsGeneralHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsGeneralHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {

        logger.error("Unexpected error", ex);

        return ResponseEntity.internalServerError().body(new ErrorResponse("Something went wrong"));
    }

    @ExceptionHandler(BusinessExceptions.class)
    public ResponseEntity<Object> handleBusinessExceptions(BusinessExceptions ex) {

        logger.error("Business error: {}", ex.getMessage(), ex);

        return ResponseEntity.internalServerError().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(InvalidReturnQuantityException.class)
    public ResponseEntity<Object> handleInvalidReturnQuantity(InvalidReturnQuantityException ex) {

        logger.warn("Invalid return quantity: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(NoSuchFieldException.class)
    public ResponseEntity<Object> handleNoSuchFieldException(NoSuchFieldException ex) {
        logger.warn("Resource not found: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ReturnPeriodExceededException.class)
    public ResponseEntity<Object> handleReturnPeriodExceeded(ReturnPeriodExceededException ex) {
        logger.warn("Return period exceeded: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFound(OrderNotFoundException ex) {
        logger.warn("Order not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }
    // **********************************************************************************************

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        logger.error("HttpMessageNotReadableException is thrown");

        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse("Unable to validate request parameters");

        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    public ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String invalidParameter = ex.getPropertyName();
        Object invalidValue = ex.getValue();
        String typeExpected =  ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown type";

        ErrorResponse errorResponse = new ErrorResponse("Invalid type of required property '" + invalidParameter + "' returns '" + invalidValue + "'. Expected type: '" + typeExpected + "'.");

        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

}