package com.example.finra.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity exceptionHandler(RuntimeException e){
        log.error("exception handling: {}" , e);
        return new ResponseEntity(e.getCause().getCause().getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class,
            MaxUploadSizeExceededException.class, MissingServletRequestPartException.class, MissingServletRequestParameterException.class})
    public ResponseEntity exceptionHandler(Exception e){
        log.error("exception handling: {}" , e);
        return new ResponseEntity(e.getCause().getCause().getMessage(), HttpStatus.BAD_REQUEST);
    }
}
