package com.example.bai4.controller;
import com.example.bai4.model.Category;
import com.example.bai4.model.Product;
import com.example.bai4.service.CategoryService;
import com.example.bai4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String Index(Model model) {
        // 'listproduct' hay 'listProduct' tùy bạn đặt, miễn là bên HTML gọi đúng tên đó
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/create")
    public String Create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        // Giả sử bạn thêm hàm remove trong ProductService
        productService.getAll().removeIf(p -> p.getId() == id);
        return "redirect:/products";
    }
    @PostMapping("/create")
    public String Create(@Valid @ModelAttribute("product") Product newProduct,
                         BindingResult result,
                         @RequestParam("categoryId") int categoryId, // Đổi cho khớp với create.html
                         @RequestParam("imageProduct") MultipartFile imageProduct,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }

        // Xử lý ảnh và category
        productService.updateImage(newProduct, imageProduct);
        Category selectedCategory = categoryService.get(categoryId);
        newProduct.setCategory(selectedCategory);

        productService.add(newProduct);
        return "redirect:/products";
    }
// Trong file ProductController.java

    @GetMapping("/edit/{id}")
    public String Edit(@PathVariable int id, Model model) {
        Product find = productService.get(id);
        if (find == null) {
            return "redirect:/products"; // Tránh lỗi 404 bằng cách quay về danh sách
        }
        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @PostMapping("/edit")
    public String Edit(@Valid @ModelAttribute("product") Product editProduct,
                       BindingResult result,
                       @RequestParam("categoryId") int categoryId, // QUAN TRỌNG: Nhận categoryId từ select
                       @RequestParam("imageProduct") MultipartFile imageProduct,
                       Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        // Lấy dữ liệu cũ để giữ lại ảnh nếu không upload ảnh mới
        Product existingProduct = productService.get(editProduct.getId());

        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct);
        } else if (existingProduct != null) {
            editProduct.setImage(existingProduct.getImage());
        }

        // Gán lại Category cho đối tượng đang sửa
        Category selectedCategory = categoryService.get(categoryId);
        editProduct.setCategory(selectedCategory);

        productService.update(editProduct);
        return "redirect:/products";
    }
}