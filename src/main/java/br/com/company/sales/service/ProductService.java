package br.com.company.sales.service;

import br.com.company.sales.entity.Product;
import br.com.company.sales.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(String description, BigDecimal price){
        Product product = new Product();
        product.setDescription(description);
        product.setPrice(price);
        return productRepository.save(product);
    }

    public void update(Integer id, Product product){
        productRepository.findById(id).map(
                productUpdate -> {
                    product.setId(id);
                    productRepository.save(product);
                    return product;
                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto inexistente!"));
    }

    public void delete(Integer id){
        productRepository.findById(id).map(
                product -> {
                    productRepository.delete(product);
                    return product;
                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto inexistente!"));
    }
}
