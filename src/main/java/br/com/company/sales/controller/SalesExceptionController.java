package br.com.company.sales.controller;

import br.com.company.sales.exception.SalesException;
import br.com.company.sales.exception.StandardErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class SalesExceptionController {

    @ExceptionHandler(SalesException.class)
    public ResponseEntity<StandardErrorResponse> salesException(SalesException exception, HttpServletRequest request){
        StandardErrorResponse standardErrorResponse = new StandardErrorResponse(exception.getMessage());
        standardErrorResponse.setTimestamp(Instant.now());
        standardErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        //TODO pensar melhor nesse título.
        //standardErrorResponse.setError("Title error");
        //standardErrorResponse.setMessage(new ApiErrors());
        standardErrorResponse.setPath(request.getRequestURI());

        return ResponseEntity.status(standardErrorResponse.getStatus()).body(standardErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<String> erros = exception
                                .getBindingResult()
                                .getAllErrors()
                                .stream()
                                .map(
                                        error -> error.getDefaultMessage()
                                ).collect(Collectors.toList());

        StandardErrorResponse standardErrorResponse = new StandardErrorResponse(erros);
        standardErrorResponse.setTimestamp(Instant.now());
        standardErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(standardErrorResponse.getStatus()).body(standardErrorResponse);
    }

}
