package com.example.bai4.service;

import com.example.bai4.model.Category;
import com.example.bai4.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @PostConstruct
    public void init() {
        if (categoryRepository.count() == 0) {
            Category category1 = new Category();
            category1.setName("Phần mềm");
            categoryRepository.save(category1);

            Category category2 = new Category();
            category2.setName("Phần cứng");
            categoryRepository.save(category2);

            Category category3 = new Category();
            category3.setName("Điện thoại");
            categoryRepository.save(category3);
        }
    }
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    public Category get(int id) {
        return categoryRepository.findById(id).orElse(null);
    }
    public void add(Category category){
        categoryRepository.save(category);
    }
    public void delete(int id){
        categoryRepository.deleteById(id);
    }
}