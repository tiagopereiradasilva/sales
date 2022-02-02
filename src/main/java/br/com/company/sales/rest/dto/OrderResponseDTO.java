package br.com.company.sales.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderResponseDTO {

    private Integer code;
    private String cpf;
    private String nameClient;
    private String date;
    private List<ItemOrderResponseDTO> items;
    private BigDecimal total;
    private String status;

}
