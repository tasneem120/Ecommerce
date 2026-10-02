package com.example.FirstProject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobaleExceptionHandeler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity< Map<String, String>> handleValidationException(MethodArgumentNotValidException ex ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( error -> {
            String fieldName=((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return  new  ResponseEntity<Map<String,String>>( errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public  ResponseEntity<ApiResponse>myResourceNotFoundException(ResourceNotFoundException e){
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return  new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(APIException.class)
    public  ResponseEntity<ApiResponse>myAPIException(APIException e){
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return  new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);

    }
}
