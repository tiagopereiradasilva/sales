package br.com.company.sales.controller;

import br.com.company.sales.entity.Order;
import br.com.company.sales.rest.dto.OrderDTO;
import br.com.company.sales.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pedidos")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody OrderDTO orderDTO){
        Order order = orderService.save(orderDTO);
        return order.getId();
    }
}
