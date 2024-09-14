package com.niladri.ecommerce.service.category;

import com.niladri.ecommerce.dto.category.CategoryPayloadDto;
import com.niladri.ecommerce.dto.category.CategoryResponseDto;

public interface CategoryServiceInterface {
    public CategoryPayloadDto createCategory(String categoryName);
    public CategoryPayloadDto updateCategory(String categoryName,long categoryId);
    public CategoryPayloadDto deleteCategory(long categoryId);
    public CategoryResponseDto getCategory(long categoryId);
    public CategoryResponseDto getCategories(Integer pageNo, Integer limit, String sortBy,String sortOrder);
}
