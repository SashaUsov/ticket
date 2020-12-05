package com.testproject.ticket.exception.handler;

import com.testproject.ticket.domain.dto.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerService {

    @ExceptionHandler({
    })
    public final ResponseEntity<ErrorInfo> handleException(Exception ex, WebRequest request) {
        var headers = new HttpHeaders();

        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error("Unexpected exception", ex);
        return handleExceptionInternal(ex, null, headers, status, request);

    }

    /**
     * A single place to customize the response body of all Exception types.
     */
    private ResponseEntity<ErrorInfo> handleExceptionInternal(Exception ex, ErrorInfo body, HttpHeaders headers,
                                                              HttpStatus status, WebRequest request
    ) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }
}
