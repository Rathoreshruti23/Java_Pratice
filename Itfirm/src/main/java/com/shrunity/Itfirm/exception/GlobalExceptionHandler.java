package com.shrunity.Itfirm.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler { //makes this class a global error handler.
//    It listens to all exceptions thrown by any controller in your app.
    @ExceptionHandler(MethodArgumentNotValidException.class) //MethodArgumentNotValidException → thrown automatically when validation fails
    public ResponseEntity<Map<String , String>> handleValidation(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>(); // create map to store the error pairs : {
       // "productName"(String): "Product name cannot be blank"(String),
         //       "price": "Price must be positive"

        ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

//        gives a list of all field validation errors.
//.forEach(...) loops through each invalid field.
//                For every error:
//        e.getField() → gives the field name (e.g., productName)
//        e.getDefaultMessage() → gives the validation message (e.g., “cannot be blank”)
//        These are stored in the errors map.
    }
    @ExceptionHandler(InvalidPriceException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPrice(InvalidPriceException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}



