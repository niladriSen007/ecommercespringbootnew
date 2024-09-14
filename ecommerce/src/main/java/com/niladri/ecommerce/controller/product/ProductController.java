package com.niladri.ecommerce.controller.product;

import com.niladri.ecommerce.dto.product.ProductPayloadDto;
import com.niladri.ecommerce.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/public/categories/product")
    public ResponseEntity<ProductPayloadDto> createProduct(@RequestBody ProductPayloadDto product,
                                                           @RequestParam(name = "categoryId",required = true) Long categoryId) {
        return new ResponseEntity<>(productService.createProduct(product,categoryId), HttpStatus.CREATED);
    }
}
