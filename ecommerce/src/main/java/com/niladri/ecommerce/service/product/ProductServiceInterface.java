package com.niladri.ecommerce.service.product;

import com.niladri.ecommerce.dto.product.ProductPayloadDto;
import com.niladri.ecommerce.dto.product.ProductResponseDto;

public interface ProductServiceInterface {
    public ProductPayloadDto createProduct(ProductPayloadDto productPayloadDto,Long categoryId);
    public ProductResponseDto getAllProducts(Integer pageNo, Integer limit, String sortBy, String sortOrder);

    public ProductResponseDto getAllProductsByCategory(Long categoryId, Integer pageNo, Integer limit, String sortBy, String sortOrder);

    ProductResponseDto getProductsByKeyword(String keyword, Integer pageNo, Integer limit, String sortBy, String sortOrder);

    ProductPayloadDto updateProduct(ProductPayloadDto product, Long productId);

    ProductPayloadDto deleteProduct(Long productId);
}
