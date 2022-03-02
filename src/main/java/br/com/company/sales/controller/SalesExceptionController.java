package br.com.company.sales.controller;

import br.com.company.sales.exception.SalesException;
import br.com.company.sales.rest.dto.ResponseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@RestControllerAdvice
public class SalesExceptionController {

    private ResponseTemplate response;
    private String messageResponse = "Ops! Algo deu errado";
    @ExceptionHandler(SalesException.class)
    public ResponseEntity<ResponseTemplate> salesException(SalesException exception, HttpServletRequest request){
        var errors = exception.getMessage();
        response = new ResponseTemplate(errors, messageResponse);
        response.setError(true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseTemplate> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        var errors = exception
                                .getBindingResult()
                                .getAllErrors()
                                .stream()
                                .map(
                                        error -> error.getDefaultMessage()
                                ).collect(Collectors.toList());
        response = new ResponseTemplate(errors, messageResponse);
        response.setError(true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseTemplate> usernameNotFoundException(UsernameNotFoundException exception){
        var errors = exception.getMessage();
        response = new ResponseTemplate(errors, messageResponse);
        response.setError(true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseTemplate> httpMessageNotReadableException(HttpMessageNotReadableException exception){
        var errors = exception.getLocalizedMessage();
        response = new ResponseTemplate(errors, messageResponse);
        response.setError(true);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }



}
