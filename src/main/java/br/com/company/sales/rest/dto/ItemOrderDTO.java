package br.com.company.sales.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemOrderDTO {
    private Integer product;
    private Integer amount;
}
