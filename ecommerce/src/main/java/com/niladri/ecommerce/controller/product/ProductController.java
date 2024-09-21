package com.niladri.ecommerce.controller.product;

import com.niladri.ecommerce.config.AppConstants;
import com.niladri.ecommerce.dto.product.ProductPayloadDto;
import com.niladri.ecommerce.dto.product.ProductResponseDto;
import com.niladri.ecommerce.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponseDto> getProductList(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
            @RequestParam(name = "limit", defaultValue = AppConstants.LIMIT, required = false) Integer limit,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder
    ) {
        return new ResponseEntity<>(productService.getAllProducts(pageNo, limit, sortBy, sortOrder), HttpStatus.OK);
    }

    @PostMapping("/admin/categories/product")
    public ResponseEntity<ProductPayloadDto> createProduct(@Valid @RequestBody ProductPayloadDto product,
                                                           @RequestParam(name = "categoryId",required = true) Long categoryId) {
        return new ResponseEntity<>(productService.createProduct(product,categoryId), HttpStatus.CREATED);
    }


    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponseDto> getProductListByCategory(
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
            @RequestParam(name = "limit", defaultValue = AppConstants.LIMIT, required = false) Integer limit,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder
    ) {
        return new ResponseEntity<>(productService.getAllProductsByCategory(categoryId,pageNo, limit, sortBy, sortOrder)
                , HttpStatus.OK);
    }

    @GetMapping("/public/products/{keyword}")
    public ResponseEntity<ProductResponseDto> searchProduct(
            @PathVariable String keyword,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
            @RequestParam(name = "limit", defaultValue = AppConstants.LIMIT, required = false) Integer limit,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder
    ) {
        return new ResponseEntity<>(productService.getProductsByKeyword(keyword,pageNo, limit, sortBy, sortOrder)
                , HttpStatus.OK);
    }

    @PutMapping("/admin/products/update/{productId}")
    public ResponseEntity<ProductPayloadDto> updateProduct(@Valid @RequestBody ProductPayloadDto product,
                                                           @PathVariable Long productId) {
        return new ResponseEntity<>(productService.updateProduct(product,productId), HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/delete/{productId}")
    public ResponseEntity<ProductPayloadDto> deleteProduct(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductPayloadDto> updateProductImage(
            @RequestParam(name = "Image",required = true)MultipartFile image,
            @PathVariable Long productId) throws IOException {
        return new ResponseEntity<>(productService.updateProductImage(image,productId), HttpStatus.OK);
    }
}
