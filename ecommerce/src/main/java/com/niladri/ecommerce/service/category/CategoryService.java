package com.niladri.ecommerce.service.category;

import com.niladri.ecommerce.model.category.CategoryModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements CategoryServiceInterface {

    private final List<CategoryModel> categoryList = new ArrayList<CategoryModel>();

    @Override
    public void createCategory(String categoryName) {
        CategoryModel category = new CategoryModel();
        category.setCategoryId(categoryList.size() + 1);
        category.setCategoryName(categoryName);
        categoryList.add(category);
    }

    @Override
    public void updateCategory(String categoryName, long categoryId) {
        CategoryModel category = categoryList.stream().filter(cat -> cat.getCategoryId() == categoryId).findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        category.setCategoryName(categoryName);
    }

    @Override
    public void deleteCategory(long categoryId) {
        CategoryModel category = categoryList.stream().filter(cat -> cat.getCategoryId() == categoryId).findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        categoryList.remove(category);
    }

    @Override
    public CategoryModel getCategory(long categoryId) {
        return null;
    }

    @Override
    public List<CategoryModel> getCategories() {
        return categoryList;
    }
}
