package com.example.productcatalogservice.clients;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FakeStoreApiClient {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductDto getProductDetails(Long productId){

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                requestForEntity("https://fakestoreapi.com/products/{productID}",
                        HttpMethod.GET,null,FakeStoreProductDto.class,productId);

        return validateResponseEntity(fakeStoreProductDtoResponseEntity);

    }

    public FakeStoreProductDto saveProduct(FakeStoreProductDto fakeStoreProductDto){

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                requestForEntity("https://fakestoreapi.com/products",
                        HttpMethod.POST,fakeStoreProductDto,FakeStoreProductDto.class);

        return validateResponseEntity(fakeStoreProductDtoResponseEntity);

    }

    public List<FakeStoreProductDto> getAllProductDetails() {

        List<FakeStoreProductDto> fakeStoreProductDto = new ArrayList<>();

        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtoResponseEntity =
                requestForEntity("https://fakestoreapi.com/products",
                        HttpMethod.GET,null,FakeStoreProductDto[].class);

        fakeStoreProductDto  = List.of(Objects.requireNonNull(validateResponseEntityForArray(fakeStoreProductDtoResponseEntity)));

        return fakeStoreProductDto;

    }

    public FakeStoreProductDto replaceProduct(Long id, FakeStoreProductDto fakeStoreProductDto) {

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                requestForEntity("https://fakestoreapi.com/products/{id}",
                        HttpMethod.PUT,fakeStoreProductDto,FakeStoreProductDto.class,id);

        return validateResponseEntity(fakeStoreProductDtoResponseEntity);

    }

    private FakeStoreProductDto[] validateResponseEntityForArray(ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtoResponseEntity){

        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200))){
            return fakeStoreProductDtoResponseEntity.getBody();
        }

        return null;
    }

    private FakeStoreProductDto validateResponseEntity(ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity){

        if(fakeStoreProductDtoResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200))){
            return fakeStoreProductDtoResponseEntity.getBody();
        }

        return null;
    }

    // Common Rest Template
    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
    // Common Rest Template

}
