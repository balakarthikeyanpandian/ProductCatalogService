package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProductController.class)
public class ProductControllerMvcTest {

    @MockBean
    private IProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void Test_GetAllProducts_RunsSuccessfully() throws Exception {

        Product product = new Product();
        product.setId(1L);
        product.setName("Iphone 16");

        Product product1 = new Product();
        product1.setId(2L);
        product1.setName("Iphone 15");

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product);


        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Iphone 16");

        ProductDto productDto1 = new ProductDto();
        productDto1.setId(2L);
        productDto1.setName("Iphone 15");

        List<ProductDto> productDos = new ArrayList<>();
        productDos.add(productDto1);
        productDos.add(productDto);


        when(productService.getAllProductDetails()).thenReturn(productList);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDos)));

    }

}
