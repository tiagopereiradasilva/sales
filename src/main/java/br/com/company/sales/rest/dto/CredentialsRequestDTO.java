package br.com.company.sales.rest.dto;

import lombok.Data;

@Data
public class CredentialsRequestDTO {
    private String username;
    private String password;
}
