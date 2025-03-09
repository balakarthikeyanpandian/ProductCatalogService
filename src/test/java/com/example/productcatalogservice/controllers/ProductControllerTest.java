package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    ProductController productController;

    @MockBean
    IProductService productService;

    @Test
    @DisplayName("Product id as 4 results with the valid product.")
    public void Test_GetProductById_WhenValidProductId_ReturnsValidProduct(){

        //Arrange
        Long productId = 7L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Iphone");
        when(productService.getProductDetails(productId)).thenReturn(product);

        //Act
        ResponseEntity<ProductDto> responseEntity = productController.findProductById(productId);

        //Assert
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        assertEquals(productId,responseEntity.getBody().getId());
        assertEquals("Iphone",responseEntity.getBody().getName());
        verify(productService,times(1)).getProductDetails(productId);

    }

    @Test
    @DisplayName("Product id as 0 results exceptions with a valid message")
    public void Test_GetProductById_WhenProductIdIs0_ThenReturn_ExceptionWithMessage(){

        //Arrange

        //Act and assert
        Exception exception = assertThrows(IllegalArgumentException.class, ()-> productController.findProductById(0L));
        assertEquals("Please try with value greater than 0",exception.getMessage());

    }

    @Test
    @DisplayName("Product Id as -1 results exception with a valid message")
    public void Test_GetProductById_WhenNegativeValueIsPassed_ThenThrowExceptionWithMessage(){

        //Act and assert
        Exception exception = assertThrows(IllegalArgumentException.class,() -> productController.findProductById(-1L));
        assertEquals("The product id can not be negative",exception.getMessage());
        verify(productService,times(0)).getProductDetails(-1L);
    }

}