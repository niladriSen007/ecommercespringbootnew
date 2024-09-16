package com.niladri.ecommerce.repository.product;

import com.niladri.ecommerce.model.category.CategoryModel;
import com.niladri.ecommerce.model.product.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductModel,Long> {
    ProductModel findByProductName(String productName);

    Page<ProductModel> findByCategory(CategoryModel categoryModel, Pageable pageDetails);

    Page<ProductModel> findByProductNameLikeIgnoreCase(String keyword,Pageable pageDetails);
}
