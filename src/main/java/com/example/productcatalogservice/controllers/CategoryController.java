package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.models.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @GetMapping
    public List<Category> getAllCategories(){

        Category category = new Category();
        Category category1 = new Category();

        category.setId(1L);
        category1.setId(2L);
        category.setName("Mobiles");
        category1.setName("Laptops");

        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category);

        return categories;


    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable("id") Long id){

        Category category = new Category();
        category.setId(id);
        category.setName("Food");
        return category;

    }

}
