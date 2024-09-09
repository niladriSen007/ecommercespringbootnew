package com.niladri.ecommerce.service.category;

import com.niladri.ecommerce.model.category.CategoryModel;
import com.niladri.ecommerce.repository.category.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryServiceInterface {

//    private final List<CategoryModel> categoryList = new ArrayList<CategoryModel>();

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public void createCategory(String categoryName) {
        CategoryModel category = new CategoryModel();
        category.setCategoryName(categoryName);
        category.setCategoryId(categoryRepo.count() + 1);
        categoryRepo.save(category);
    }

    @Override
    public void updateCategory(String categoryName, long categoryId) {
        Optional<CategoryModel> category = Optional.of
                (categoryRepo.findById(categoryId).orElseThrow(
                        () -> new ResponseStatusException
                                (HttpStatus.NOT_FOUND, "Category not found")));
        category.get().setCategoryName(categoryName);
        category.get().setCategoryId(categoryId);
        categoryRepo.save(category.get());
    }

    @Override
    public void deleteCategory(long categoryId) {
       Optional<CategoryModel> category = Optional.of
               (categoryRepo.findById(categoryId).orElseThrow(
                       () -> new ResponseStatusException
                               (HttpStatus.NOT_FOUND, "Category not found")));
         categoryRepo.delete(category.get());
    }

    @Override
    public CategoryModel getCategory(long categoryId) {
        Optional<CategoryModel> category = Optional.of
                (categoryRepo.findById(categoryId).orElseThrow(
                        () -> new ResponseStatusException
                                (HttpStatus.NOT_FOUND, "Category not found")));
        return category.get();
    }

    @Override
    public List<CategoryModel> getCategories() {
        return categoryRepo.findAll();
    }
}
