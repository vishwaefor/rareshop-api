package com.rareshop.api.rest.listing.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rareshop.api.common.core.exception.BadRequestException;
import rareshop.api.common.core.exception.ForbiddenException;
import rareshop.api.common.core.exception.InvalidArgumentException;
import rareshop.api.common.core.exception.NotFoundException;
import rareshop.api.common.core.model.RareResponse;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {BadRequestException.class})
    protected ResponseEntity<Object> handleBadRequests(
            RuntimeException ex, WebRequest request) {

        return sendErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value
            = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundErrors(
            RuntimeException ex, WebRequest request) {

        return sendErrorResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value
            = {ForbiddenException.class})
    protected ResponseEntity<Object> handleForbiddenErrors(
            RuntimeException ex, WebRequest request) {

        return sendErrorResponse(ex, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value
            = {InvalidArgumentException.class})
    protected ResponseEntity<Object> handleInvalidArgsErrors(
            RuntimeException ex, WebRequest request) {

        return sendErrorResponse(ex, request, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<Object> sendErrorResponse(
            RuntimeException ex, WebRequest request, HttpStatus status) {

        RareResponse bodyOfResponse = new RareResponse(ex.getMessage(), false);

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), status, request);
    }
}