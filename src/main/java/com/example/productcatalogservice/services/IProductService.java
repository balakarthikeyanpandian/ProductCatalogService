package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Product;

public interface IProductService {
    Product getProductDetails(Long productId);
}
