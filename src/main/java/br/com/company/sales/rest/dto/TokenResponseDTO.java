package br.com.company.sales.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenResponseDTO {
    private Integer userId;
    private String accessToken;
    private long expiresIn;
    private String type;
}
