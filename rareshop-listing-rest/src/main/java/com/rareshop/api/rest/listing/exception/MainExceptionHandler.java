package com.rareshop.api.rest.listing.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rareshop.api.common.core.exception.BadRequestException;
import rareshop.api.common.core.exception.NotFoundException;
import rareshop.api.common.core.model.RareResponse;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {BadRequestException.class})
    protected ResponseEntity<Object> handleBadRequests(
            RuntimeException ex, WebRequest request) {

        RareResponse bodyOfResponse = new RareResponse(ex.getMessage(), false);

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundRequests(
            RuntimeException ex, WebRequest request) {

        RareResponse bodyOfResponse = new RareResponse(ex.getMessage(), false);

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}