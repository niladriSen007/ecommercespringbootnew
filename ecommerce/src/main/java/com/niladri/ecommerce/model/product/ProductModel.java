package com.niladri.ecommerce.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.niladri.ecommerce.model.category.CategoryModel;
import com.niladri.ecommerce.model.user.UserModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@ToString
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank
    @Size(min = 3, max = 50, message = "Product name should be between 3 to 50 characters")
    private String productName;
    @NotBlank
    @Size(min = 6, max = 50, message = "Product name should be between 6 to 50 characters")
    private String productDescription;

    @NotNull
    @Positive
    @Min(value = 100, message = "Product price should be greater than 100")
    private double productPrice;
    private double productDiscount;

    @NotNull
    @Positive
    @Min(value = 1, message = "Product quantity should be greater than 1")
    private Integer productQuantity;
    private double productSpecialPrice;
    private String productImage;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private CategoryModel category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonIgnore
    private UserModel user;


}
