package br.com.company.sales.controller;

import br.com.company.sales.entity.Order;
import br.com.company.sales.rest.dto.OrderDTO;
import br.com.company.sales.rest.dto.OrderResponseDTO;
import br.com.company.sales.rest.dto.StatusOrderRequestDTO;
import br.com.company.sales.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("api/pedidos")
public class OrderController {
    private OrderService orderService;

    public OrderController(@Autowired OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid OrderDTO orderDTO){
        Order order = orderService.save(orderDTO);
        return order.getId();
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Integer id){
        return ResponseEntity.ok(orderService.getCompletedOrder(id));
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id,
                             @RequestBody StatusOrderRequestDTO statusOrderRequestDTO){
        orderService.updateStatus(id, statusOrderRequestDTO);

    }
}
