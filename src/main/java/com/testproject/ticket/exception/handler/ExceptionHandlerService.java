package com.testproject.ticket.exception.handler;

import com.testproject.ticket.domain.dto.ex.ApiError;
import com.testproject.ticket.domain.dto.ex.ErrorInfo;
import com.testproject.ticket.exception.BusinessExceptions;
import com.testproject.ticket.exception.DataIsNotCorrectException;
import com.testproject.ticket.exception.EntityNotFoundException;
import com.testproject.ticket.exception.PermissionToActionIsAbsentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerService {

    @ExceptionHandler({DataIsNotCorrectException.class,
            EntityNotFoundException.class,
            PermissionToActionIsAbsentException.class,
            MethodArgumentNotValidException.class,
            Exception.class
    })
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {

        if (ex instanceof PermissionToActionIsAbsentException) {
            var status = HttpStatus.FORBIDDEN;
            var ptaiae = (PermissionToActionIsAbsentException) ex;

            log.error("Attempt to access content without proper authority", ex);
            return handleBusinessExceptions(ptaiae, status);
        } else if (ex instanceof MethodArgumentNotValidException) {
            var status = HttpStatus.BAD_REQUEST;
            var manve = (MethodArgumentNotValidException) ex;

            return handleMethodArgumentNotValidException(manve, status);
        } else if (ex instanceof EntityNotFoundException) {
            var status = HttpStatus.NOT_FOUND;
            var be = (BusinessExceptions) ex;

            log.error("Conflict with existing content", ex);
            return handleBusinessExceptions(be, status);
        } else if (ex instanceof DataIsNotCorrectException) {
            var status = HttpStatus.BAD_REQUEST;
            var dince = (DataIsNotCorrectException) ex;

            log.error("The received data is not correct.", ex);
            return handleBusinessExceptions(dince, status);
        } else {
            var status = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("Unexpected exception", ex);
            return handleExceptionInternal(ex, status, request);
        }
    }

    /**
     * Customize the response for BusinessExceptions.
     */
    private ResponseEntity<ApiError> handleBusinessExceptions(BusinessExceptions ex, HttpStatus status) {

        return new ResponseEntity<>(new ApiError("business_exceptions",
                Collections.singleton(new ErrorInfo(status.toString(), ex.getMessage()))), status);
    }

    /**
     * Customize the response for MethodArgumentNotValidException.
     */
    private ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpStatus status) {
        log.warn("The data required to make a request is not valid", ex);
        var errorMessages = ex.getAllErrors().stream().map(x -> new ErrorInfo(x.getCode(), x.getDefaultMessage()));
        return new ResponseEntity<>(new ApiError("method_argument_not_valid_exception",
                errorMessages.collect(Collectors.toSet())), status);
    }

    /**
     * A single place to customize the response body of all Exception types.
     */
    private ResponseEntity<ApiError> handleExceptionInternal(Exception ex, HttpStatus status, WebRequest request
    ) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(new ApiError("unexpected_exception",
                Collections.singleton(new ErrorInfo(status.toString(), ex.getMessage()))), status);
    }
}
