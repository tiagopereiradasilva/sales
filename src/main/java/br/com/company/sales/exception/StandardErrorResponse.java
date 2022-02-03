package br.com.company.sales.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class StandardErrorResponse {


    private Integer status;
    private List<String> errors;
    private List<String> messages;
    private String path;
    private Instant timestamp;

    public StandardErrorResponse(String error) {
        this.errors = List.of(error);
    }

    public StandardErrorResponse(List<String> errors) {
        this.errors = errors;
    }
}
