package com.example.productcatalogservice_dec2024.controllers;

import com.example.productcatalogservice_dec2024.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping
    public List<Product> getAllProducts(){

        Product product1 = new Product();
        product1.setId(1L);
        product1.setDescription("Phone");
        product1.setName("Iphone 16");

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        return productList;

    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable("id") Long id){

        Product product1 = new Product();
        product1.setId(id);
        product1.setDescription("Phone");
        product1.setName("Iphone 16");
        return product1;

    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){

        Product product1 = new Product();
        product1.setId(product.getId());
        product1.setPrice(product.getPrice());
        product1.setDescription(product.getDescription());
        return product1;

    }
}
