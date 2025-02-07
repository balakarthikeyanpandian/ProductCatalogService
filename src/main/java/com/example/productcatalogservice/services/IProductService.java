package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductDetails(Long productId);
    Product saveProduct(Product product);
    List<Product> getAllProductDetails();
    Product replaceProduct(Long id, Product product);
}
