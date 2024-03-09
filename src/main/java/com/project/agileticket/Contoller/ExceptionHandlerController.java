package com.project.agileticket.Contoller;

import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;


import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ExceptionHandlerController {
    private String responseHandler(HttpStatus status){
        return "redirect:/user/welcomelogin";
    }
    @ExceptionHandler(InvalidInputException.class)
    public String handleInvalidInputException(InvalidInputException ie) {
        System.err.println(ie.getMessage());
        return responseHandler(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentInvalidException(MethodArgumentNotValidException me) {
        System.err.println(me.getMessage());
        return responseHandler(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleHttpMessageNotReadable(HttpMessageNotReadableException he) {
        System.err.println(he.getMessage());
        return responseHandler(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException() {

        return responseHandler(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public String handleMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException hme){
        System.out.println("handleMediaTypeNotSupportedExceptionHandler");
        System.err.println(hme.getMessage());
        return responseHandler(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedExceptionHandler(AccessDeniedException ae){
        System.err.println(ae.getMessage());
        return responseHandler(HttpStatus.UNAUTHORIZED);
    }
}
