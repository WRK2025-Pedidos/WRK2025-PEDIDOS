package com.gft.orders.domain.exceptions;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsGeneralHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
//
//        logger.error("Unexpected error", ex);
//
//        return ResponseEntity.internalServerError().body(new ErrorResponse("Something went wrong"));
//    }
//
//    @ExceptionHandler(BusinessExceptions.class)
//    public ResponseEntity<Object> handleBusinessExceptions(BusinessExceptions ex) {
//
//        logger.error("JSON object cannot be deserialized");
//
//        return ResponseEntity.internalServerError().body(new ErrorResponse(ex.getMessage()));
//    }
//
//    // **********************************************************************************************
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//
//        logger.error("HttpMessageNotReadableException is thrown");
//
//        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//
//        ErrorResponse errorResponse = new ErrorResponse("Unable to validate request parameters");
//
//        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//
//        String invalidParameter = ex.getPropertyName();
//        Object invalidValue = ex.getValue();
//        String typeExpected = != null ? requiredType.getSimpleName() : null;
//
//        if (typeExpected == null) {
//            throw new NullPointerException("Type of required property '" + invalidParameter + "' is null");
//        }
//
//        ErrorResponse errorResponse = new ErrorResponse("Invalid type of required property '" + invalidParameter + "' returns '" + invalidValue + "'. The following type was expected: '" + typeExpected + "'.");
//
//        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
//    }

}