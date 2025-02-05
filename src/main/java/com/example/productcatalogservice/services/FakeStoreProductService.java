package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements IProductService{

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public Product getProductDetails(Long productId){

        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto>  fakeStoreProductDtoResponseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products/{productID}",FakeStoreProductDto.class,productId);

        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakeStoreProductDtoResponseEntity.getBody() != null){
            return from(fakeStoreProductDtoResponseEntity.getBody());
        }

        return null;
    }

    public Product save(Product product){

        RestTemplate restTemplate = restTemplateBuilder.build();



        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = restTemplate.postForEntity("https://fakestoreapi.com/products",from(product),FakeStoreProductDto.class);

        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200))){
            return from(fakeStoreProductDtoResponseEntity.getBody());
        }

        return null;
    }

    private FakeStoreProductDto from(Product product){

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();

        fakeStoreProductDto.setCategory(product.getCategory().getName());
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageURL());

        return fakeStoreProductDto;
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        product.setImageURL(fakeStoreProductDto.getImage());
        return product;
    }

}
