package br.com.company.sales.controller;

import br.com.company.sales.entity.Product;
import br.com.company.sales.exception.SalesException;
import br.com.company.sales.repository.ProductRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/produtos")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Integer id){
        return productRepository.findById(id).orElseThrow(
                () -> new SalesException("Produto não encontrado!")
        );
    }

    @GetMapping
    public List<Product> find(Product productFilter){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Product> example = Example.of(productFilter, exampleMatcher);
        return productRepository.findAll(example);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody @Valid Product product){
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Product product){
        productRepository.findById(id).map(
                productUpdated -> {
                    product.setId(id);
                    return productRepository.save(product);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto inexistente!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
           productRepository.findById(id).map(
                   product -> {
                       productRepository.delete(product);
                       return product;
                   }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto inexistente!"));
    }

}
