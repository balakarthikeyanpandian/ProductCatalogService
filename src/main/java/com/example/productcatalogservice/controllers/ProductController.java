package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){

        List<ProductDto> productDtos = new ArrayList<>();

        List<Product> products = productService.getAllProductDetails();

        for(Product product : products){
            productDtos.add(from(product));
        }
        return new ResponseEntity<>(productDtos,HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable("id") Long id){

        LinkedMultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add("copy-right","2025");

        if(id<=0){
            return new ResponseEntity<>(new ProductDto(), headers,  HttpStatus.NOT_FOUND);
        }

        Product product = productService.getProductDetails(id);

        if(product == null){
            return new ResponseEntity<>(new ProductDto(), headers, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(from(product), headers, HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){

        LinkedMultiValueMap<String,String> headers = new LinkedMultiValueMap<>();

        headers.add("product-creation","yes");

        Product input = from(productDto);
        Product output = productService.save(input);
        if(output !=null)
            return new ResponseEntity<>(from(output), headers, HttpStatus.OK);

        return new ResponseEntity<>(new ProductDto(), headers, HttpStatus.BAD_REQUEST);

        //check from fake store api

    }

    // Mappers
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

    private Product from(ProductDto productDto){

        Product product = new Product();

        product.setImageURL(productDto.getImageURL());
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setId(productDto.getId());

        CategoryDto categoryDto = productDto.getCategory();
        if(categoryDto!=null){
            Category category = new Category();
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());
            category.setId(categoryDto.getId());

            product.setCategory(category);
        }

        return product;

    }
    // Mappers

}
