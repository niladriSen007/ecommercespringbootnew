package com.niladri.ecommerce.controller.category;

import com.niladri.ecommerce.model.category.CategoryModel;
import com.niladri.ecommerce.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/public/category")
    public ResponseEntity<List<CategoryModel>> getCategoryList() {
        try {
            List<CategoryModel> categoryList = categoryService.getCategories();
            return ResponseEntity.status(HttpStatus.OK).body(categoryList);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }

    @PostMapping("/public/category")
    public String createCategory(@RequestBody CategoryModel category) {
        categoryService.createCategory(category.getCategoryName());
        return "Category created successfully";
    }

    @PutMapping("/admin/category/update/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryModel category, @PathVariable long categoryId) {
        try {
            categoryService.updateCategory(category.getCategoryName(), categoryId);
            return ResponseEntity.ok("Category updated successfully");
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @DeleteMapping("/admin/category/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully");
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

}
