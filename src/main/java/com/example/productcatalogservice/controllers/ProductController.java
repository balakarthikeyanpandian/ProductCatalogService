package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    IProductService productService;

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
    public ProductDto findProductById(@PathVariable("id") Long id){

        return from(productService.getProductDetails(id));

    }

    private ProductDto from(Product product){

        ProductDto productDto = new ProductDto();


        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setIsPrime(true);
        productDto.setImageURL(product.getImageURL());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());

        if(product.getCategory() != null){
            CategoryDto categoryDto = new CategoryDto();
            Category category = product.getCategory();
            categoryDto.setDescription(category.getDescription());
            categoryDto.setName(category.getName());
            categoryDto.setId(category.getId());

            productDto.setCategory(categoryDto);
        }

        return productDto;

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
