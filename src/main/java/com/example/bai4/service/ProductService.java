
package com.example.bai4.service;
import com.example.bai4.model.Product;
import com.example.bai4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product get(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void add(Product newProduct) {
        productRepository.save(newProduct);
    }

    public void update(Product editProduct) {
        productRepository.save(editProduct);
    }
    public void delete(int id){
        productRepository.deleteById(id);
    }

    public void updateImage(Product newProduct, MultipartFile imageProduct) {
        String contentType = imageProduct.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Tệp tải lên không phải là hình ảnh!");
        }

        if (!imageProduct.isEmpty()) {
            try {
                Path dirImages = Paths.get("static/images");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }

                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);

                Files.copy(imageProduct.getInputStream(),
                        pathFileUpload,
                        StandardCopyOption.REPLACE_EXISTING);

                newProduct.setImage(newFileName);

            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
}