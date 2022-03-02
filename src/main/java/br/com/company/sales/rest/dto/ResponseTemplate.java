package br.com.company.sales.rest.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ResponseTemplate {
    private boolean error;
    private String message;
    private int count;
    private Long timestamp;
    private List<?> data;

    public ResponseTemplate(Object data, String message) {
        this.message = message;
        this.data = List.of(data);
        init();
    }

    public ResponseTemplate(List<?> data, String message) {
        this.data = data;
        init();
    }

    private void init(){
        this.error = false;
        this.count = getData().size();
        this.timestamp = Instant.now().getEpochSecond();
    }

}
