package com.example.bai4.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // Thêm cái này để sửa lỗi constructor 0 tham số
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Length(min = 0, max = 200, message = "Tên hình ảnh không quá 200 kí tự")
    @Column(name = "image", length = 200)
    private String image;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 1, message = "Giá sản phẩm không được nhỏ hơn 1")
    @Max(value = 9999999, message = "Giá sản phẩm không được lớn hơn 9999999")
    @Column(name = "price", nullable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}