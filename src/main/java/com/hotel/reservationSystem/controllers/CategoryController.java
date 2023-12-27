package com.hotel.reservationSystem.controllers;

import com.hotel.reservationSystem.models.Category;
import com.hotel.reservationSystem.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getCategories(){
        return categoryService.findAll();
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Category getCategory(@PathVariable int id){
        return  categoryService.find(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCategory(@RequestBody Category category){
        categoryService.save(category);
        return  ResponseEntity.status(201).body(category);
    }
}
