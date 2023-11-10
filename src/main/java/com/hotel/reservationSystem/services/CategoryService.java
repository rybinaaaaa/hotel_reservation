package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Category;
import com.hotel.reservationSystem.models.Room;
import com.hotel.reservationSystem.repositories.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Category find(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

//    do we need pagination here?
//    public List<Category> findAll(int page, int size){
//        return categoryRepository.findAll(PageRequest.of(page, size)).getContent();
//    }

    @Transactional
    public void addRoom(Category category, Room room) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(room);
        category.addRoom(room);
        update(category);
    }

    @Transactional
    public void removeRoom(Category category, Room room) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(room);
        category.removeRoom(room);
        update(category);
    }

    @Transactional
    public void update(Category category) {
        categoryRepository.save(category);
    }

    @Transactional
    public void update(int id, Category category) {
        category.setId(id);
       categoryRepository.save(category);
    }

    @Transactional
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Transactional
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }
}
