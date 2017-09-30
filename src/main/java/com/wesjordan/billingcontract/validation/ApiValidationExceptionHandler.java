package com.wesjordan.billingcontract.validation;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> invalidArgument(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();

        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode("Validation error");
        response.setErrorMessage("Invalid input");
        response.setErrors(fromBindingErrors(bindingResult));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private List<String> fromBindingErrors(Errors errors){
        List<String> errorList = new ArrayList<>();
        for(ObjectError objectError: errors.getAllErrors()){
            errorList.add(objectError.getCodes()[objectError.getCodes().length-1]);
        }
        return errorList;
    }
}
