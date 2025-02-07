package com.example.productcatalogservice.services;

import com.example.productcatalogservice.clients.FakeStoreApiClient;
import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService{

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    FakeStoreApiClient fakeStoreApiClient;

    public Product getProductDetails(Long productId){

        FakeStoreProductDto fakeStoreProductDto;
        fakeStoreProductDto = fakeStoreApiClient.getProductDetails(productId);

        if(fakeStoreProductDto!=null){
            return from(fakeStoreProductDto);
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

    @Override
    public List<Product> getAllProductDetails() {

        RestTemplate restTemplate = restTemplateBuilder.build();
        List<Product> products = new ArrayList<>();

        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtoResponseEntity =
                restTemplate.getForEntity("https://fakestoreapi.com/products/",FakeStoreProductDto[].class);

        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200))){
            List<FakeStoreProductDto> fakeStoreProductDtoList = new ArrayList<>();

            for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtoResponseEntity.getBody()){
                products.add(from(fakeStoreProductDto));
            }

            return products;

        }

        return null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {

        FakeStoreProductDto fakeStoreProductDto = from(product);

//        ResponseEntity<FakeStoreProductDto> responseEntity =
//                requestForEntity("https://fakestoreapi.com/products/{productID}",HttpMethod.PUT,fakeStoreProductDto,FakeStoreProductDto.class,id);
//
//        if(responseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200))){
//            FakeStoreProductDto fakeStoreProductDto1 = responseEntity.getBody();
//            return from(fakeStoreProductDto1);
//        }

        return null;
    }




    // Mappers
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
    // Mappers


}
