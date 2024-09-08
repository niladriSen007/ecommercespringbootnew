package com.niladri.ecommerce.service.category;

import com.niladri.ecommerce.model.category.CategoryModel;

import java.util.List;

public interface CategoryServiceInterface {
    public void createCategory(String categoryName);
    public void updateCategory(String categoryName,long categoryId);
    public void deleteCategory(long categoryId);
    public CategoryModel getCategory(long categoryId);
    public List<CategoryModel> getCategories();
}
