package com.homework.first.web;

import com.homework.first.exception.InvalidEntityException;
import com.homework.first.exception.NonexisitngEntityException;
import com.homework.first.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice("com.homework.first.web")
@ControllerAdvice(basePackageClasses = {com.homework.first.web.PostsController.class, com.homework.first.web.UsersController.class})
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NonexisitngEntityException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(InvalidEntityException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }
}
