package br.com.company.sales.controller;

import br.com.company.sales.entity.Client;
import br.com.company.sales.entity.Order;
import br.com.company.sales.repository.ClientRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/clientes")
public class ClientController {
    private ClientRepository clientRepository;

    public ClientController(@Autowired ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/{id}")
    public Client findById(@PathVariable Integer id){
        return clientRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente inexistente!"));
    }

    @GetMapping
    public List<Client> find(Client clientFilters){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                                        .withIgnoreCase()
                                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Client> example = Example.of(clientFilters, exampleMatcher);
        return clientRepository.findAll(example);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client save(@RequestBody Client client){
        return clientRepository.save(client);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Client client){
        clientRepository.findById(id).map(
            clientUpdated -> {
                client.setId(id);
                clientRepository.save(client);
                return client;
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente inexistente!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        clientRepository.findById(id).map(
                client -> {
                    clientRepository.delete(client);
                    return client;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente inexistente!"));
    }

    @GetMapping("/json")
    public JsonNode toJson() throws IOException {
        // Responsabilidade da camada Service
        ObjectMapper objectMapper = new ObjectMapper();

        Order order1 = new Order();
        order1.setId(1);
        //order1.setDateOrder(LocalDate.now());
        order1.setTotal(BigDecimal.valueOf(100));

        Order order2 = new Order();
        order2.setId(1);
        //order2.setDateOrder(LocalDate.now());
        order2.setTotal(BigDecimal.valueOf(100));

        Set<Order> orders = new HashSet<Order>();
        orders.add(order1);
        orders.add(order2);

        Client client = new Client();
        client.setId(1);
        client.setName("Tiago");
        client.setCpf("000000000");
        client.setOrders((Set<Order>) orders);

        objectMapper.writeValue(new File("target/client.json"), client);
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(client));
        return jsonNode;

    }
}
