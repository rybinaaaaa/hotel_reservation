package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Category;
import com.hotel.reservationSystem.models.Room;
import com.hotel.reservationSystem.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category find(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

//    do we need pagination here?
//    public List<Category> findAll(int page, int size){
//        return categoryRepository.findAll(PageRequest.of(page, size)).getContent();
//    }

    @Transactional
    public void addRoom(Category category, Room room) {
        category.addRoom(room);
    }

    @Transactional
    public void removeRoom(Category category, Room room) {
        category.removeRoom(room);
    }

    @Transactional
    public void update(Integer id, Category category) {
        category.setId(id);
        categoryRepository.save(category);
    }

    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Transactional
    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }
}
