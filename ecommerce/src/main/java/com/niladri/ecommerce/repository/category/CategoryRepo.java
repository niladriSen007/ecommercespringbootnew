package com.niladri.ecommerce.repository.category;

import com.niladri.ecommerce.model.category.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryModel, Long> {

    CategoryModel findByCategoryName(String categoryName);
}
