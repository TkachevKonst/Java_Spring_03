package ru.gb.spring.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> hadleIllegalArgumentException (IllegalArgumentException e){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({NoSuchMethodException.class})
    public ResponseEntity<?> hadleNoSuchMethodException (NoSuchMethodException e){
        return ResponseEntity.notFound().build();
    }
}
