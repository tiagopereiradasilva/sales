package br.com.company.sales.controller;

import br.com.company.sales.exception.SalesException;
import br.com.company.sales.exception.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class SalesExceptionController {

    @ExceptionHandler(SalesException.class)
    public ResponseEntity<StandardError> salesException(SalesException exception, HttpServletRequest request){
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.BAD_REQUEST.value());
        //TODO pensar melhor nesse título.
        standardError.setError("Title error");
        standardError.setMessage(exception.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(standardError.getStatus()).body(standardError);
    }
}
