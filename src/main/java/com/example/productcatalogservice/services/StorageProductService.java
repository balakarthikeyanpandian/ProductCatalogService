package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Primary
public class StorageProductService implements IProductService{

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product getProductDetails(Long productId) {

        return productRepo.findProductById(productId);

    }

    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public List<Product> getAllProductDetails() {

        return productRepo.findAll();

    }

    @Override
    public Product replaceProduct(Long id, Product product) {

        Product product1 = productRepo.findProductById(id);
        if(product1!=null){
            return productRepo.save(product);
        }
        return null;
    }
}
