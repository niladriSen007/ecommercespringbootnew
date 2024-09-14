package com.niladri.ecommerce.service.product;

import com.niladri.ecommerce.dto.product.ProductPayloadDto;

public interface ProductServiceInterface {
    public ProductPayloadDto createProduct(ProductPayloadDto productPayloadDto,Long categoryId);
}
