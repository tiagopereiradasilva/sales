package br.com.company.sales.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ItemOrderResponseDTO {
    private String nameProduct;
    private BigDecimal unitaryValue;
    private Integer amount;
}
