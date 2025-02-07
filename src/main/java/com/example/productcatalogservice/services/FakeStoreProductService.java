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
    FakeStoreApiClient fakeStoreApiClient;

    public Product getProductDetails(Long productId){

        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.getProductDetails(productId);

        return returnAsProduct(fakeStoreProductDto);
    }

    public Product saveProduct(Product product){

        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.saveProduct(from(product));

        return returnAsProduct(fakeStoreProductDto);

    }

    public List<Product> getAllProductDetails() {

        List<FakeStoreProductDto> fakeStoreProductDto = fakeStoreApiClient.getAllProductDetails();

        return returnAsProductList(fakeStoreProductDto);

    }


    public Product replaceProduct(Long id, Product product) {

        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.replaceProduct(id,from(product));

        return returnAsProduct(fakeStoreProductDto);
    }


    private Product returnAsProduct(FakeStoreProductDto fakeStoreProductDto){
        if(fakeStoreProductDto!=null){
            return from(fakeStoreProductDto);
        }
        return null;
    }

    private List<Product> returnAsProductList(List<FakeStoreProductDto> fakeStoreProductDto){

        List<Product> products = new ArrayList<>();

        if(fakeStoreProductDto==null)
            return null;

        for(FakeStoreProductDto fakeStoreProductDto1 : fakeStoreProductDto){
            products.add(from(fakeStoreProductDto1));
        }

        return products;
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
