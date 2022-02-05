package br.com.company.sales.rest.dto;

import br.com.company.sales.validation.NotEmptyList;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    @NotNull(message = "Código do cliente é obrigatório.")
    private Integer client;
    private LocalDate dataOrder;
    @NotNull(message = "Campo total é obrigatório.")
    private BigDecimal total;
    @NotEmptyList(message = "O pedido deve conter itens.")
    private List<ItemOrderDTO> itemsOrder;
}
