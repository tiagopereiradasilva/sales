package br.com.company.sales.controller;

import br.com.company.sales.entity.Client;
import br.com.company.sales.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/clientes")
public class ClientController {
    private ClientRepository clientRepository;

    public ClientController(@Autowired ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

//    @GetMapping
//    public ResponseEntity<List<Client>> getAll(){
//        List<Client> clients = clientRepository.findAll();
//        if(clients.isEmpty()){
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(clients);
//
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Integer id){
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            return ResponseEntity.ok(client.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity find(Client clientFilters){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                                        .withIgnoreCase()
                                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(clientFilters, exampleMatcher);
        List<Client> clients = clientRepository.findAll(example);
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Client client){
        Client clientSaved = clientRepository.save(client);
        return ResponseEntity.ok(clientSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Client client){
        Optional<Client> clientUpdate = clientRepository.findById(id);
        if(clientUpdate.isPresent()){
            client.setId(id);
            clientRepository.save(client);
            return ResponseEntity.ok(client);
        }

        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> delete(@PathVariable Integer id){
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            clientRepository.delete(client.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
