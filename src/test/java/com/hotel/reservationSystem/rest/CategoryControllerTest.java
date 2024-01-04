package com.hotel.reservationSystem.rest;

import com.hotel.reservationSystem.controllers.CategoryController;
import com.hotel.reservationSystem.models.Category;
import com.hotel.reservationSystem.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    public void testGetCategories() {
        when(categoryService.findAll()).thenReturn(Arrays.asList(new Category(), new Category()));

        List<Category> result = categoryController.getCategories();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetCategory() {
        when(categoryService.find(anyInt())).thenReturn(new Category());

        Category result = categoryController.getCategory(1);

        assertEquals(Category.class, result.getClass());
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category();

        ResponseEntity result = categoryController.createCategory(category);

        verify(categoryService, times(1)).save(category);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(category, result.getBody());
    }

    @Test
    public void testGetCategoryByName() {
        when(categoryService.findByName(anyString())).thenReturn(new Category());

        Category result = categoryController.getCategoryByName("TestCategory");

        assertEquals(Category.class, result.getClass());
    }
}
