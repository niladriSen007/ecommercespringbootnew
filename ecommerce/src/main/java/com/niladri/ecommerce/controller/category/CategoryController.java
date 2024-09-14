package com.niladri.ecommerce.controller.category;

import com.niladri.ecommerce.config.AppConstants;
import com.niladri.ecommerce.dto.category.CategoryPayloadDto;
import com.niladri.ecommerce.dto.category.CategoryResponseDto;
import com.niladri.ecommerce.model.category.CategoryModel;
import com.niladri.ecommerce.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/public/category")
    public ResponseEntity<CategoryResponseDto> getCategoryList(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
            @RequestParam(name = "limit", defaultValue = AppConstants.LIMIT, required = false) Integer limit,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {
        CategoryResponseDto categoryList = categoryService.getCategories(pageNo, limit, sortBy, sortOrder);
        return ResponseEntity.status(HttpStatus.OK).body(categoryList);
    }

    @PostMapping("/public/category")
    public ResponseEntity<CategoryPayloadDto> createCategory(@Valid @RequestBody CategoryPayloadDto category) {
        CategoryPayloadDto newAddedCategory = categoryService.createCategory(category.getCategoryName());
        return new ResponseEntity<>(newAddedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/admin/category/update/{categoryId}")
    public ResponseEntity<CategoryPayloadDto> updateCategory(@Valid @RequestBody CategoryModel category, @PathVariable long categoryId) {
        CategoryPayloadDto updatedCategory = categoryService.updateCategory(category.getCategoryName(), categoryId);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/admin/category/delete/{categoryId}")
    public ResponseEntity<CategoryPayloadDto> deleteCategory(@PathVariable long categoryId) {
        CategoryPayloadDto deletedCategory = categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(deletedCategory);
    }

}
