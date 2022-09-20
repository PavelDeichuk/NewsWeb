package com.pavel.newsweb.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class CustomHandlerException extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) throws Exception {
        return handleException(ex,request);
    }
}
