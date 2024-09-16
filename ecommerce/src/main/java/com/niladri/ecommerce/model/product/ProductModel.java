package com.niladri.ecommerce.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.niladri.ecommerce.model.category.CategoryModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private double productDiscount;
    private Integer productQuantity;
    private double productSpecialPrice;
    private String productImage;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private CategoryModel category;


}
