package br.com.company.sales.rest.dto.user;


import lombok.Data;

@Data
public class UserSystemResponseDTO {
    private String username;
    private boolean admin;
}
