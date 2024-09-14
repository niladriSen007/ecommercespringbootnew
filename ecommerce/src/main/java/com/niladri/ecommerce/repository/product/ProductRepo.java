package com.niladri.ecommerce.repository.product;

import com.niladri.ecommerce.model.product.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductModel,Long> {
    ProductModel findByProductName(String productName);
}
