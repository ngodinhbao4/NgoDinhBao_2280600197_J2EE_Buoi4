package com.example.bai4.service;

import com.example.bai4.model.Category;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoryService {
    // Tạo danh sách cố định để demo
    private List<Category> categories = Arrays.asList(
            new Category(1, "Phần mềm"),
            new Category(2, "Phần cứng"),
            new Category(3, "Điện thoại")
    );

    public List<Category> getAll() {
        return categories;
    }

    // Thêm hàm này để hết lỗi trong Controller
    public Category get(int id) {
        return categories.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}