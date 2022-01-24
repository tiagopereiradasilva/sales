package br.com.company.sales.controller;

import br.com.company.sales.entity.Client;
import br.com.company.sales.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/clientes")
public class ClientController {
    private ClientRepository clientRepository;

    public ClientController(@Autowired ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable Integer id){
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
}
