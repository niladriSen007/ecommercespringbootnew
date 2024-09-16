package com.niladri.ecommerce.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPayloadDto {
    private Long productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private double productDiscount;
    private double productSpecialPrice;
    private Integer productQuantity;
    private String productImage;
    private Long categoryId;
}
