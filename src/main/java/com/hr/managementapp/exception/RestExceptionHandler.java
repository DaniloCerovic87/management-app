package com.hr.managementapp.exception;

import com.hr.managementapp.exception.domain.*;
import com.hr.managementapp.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "An error occurred while processing the request";

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<HttpResponse> employeeNotFoundException(EmployeeNotFoundException ex) {
        return createHttpResponse(NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(EmployeeNotHiredInDepartmentException.class)
    public ResponseEntity<HttpResponse> employeeNotHiredInDepartmentException(EmployeeNotHiredInDepartmentException ex) {
        return createHttpResponse(BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NotValidFilterFormException.class)
    public ResponseEntity<HttpResponse> notValidFilterFormException(NotValidFilterFormException ex) {
        return createHttpResponse(BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<HttpResponse> departmentNotFoundException(DepartmentNotFoundException ex) {
        return createHttpResponse(NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DepartmentNotSelectedException.class)
    public ResponseEntity<HttpResponse> departmentNotSelectedException(DepartmentNotSelectedException ex) {
        return createHttpResponse(BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGE);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message);
        return new ResponseEntity<>(body, httpStatus);
    }

}
