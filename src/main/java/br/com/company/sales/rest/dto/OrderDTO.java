package br.com.company.sales.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Integer client;
    private LocalDate dataOrder;
    private BigDecimal total;
    private List<ItemOrderDTO> itemsOrder;
}
