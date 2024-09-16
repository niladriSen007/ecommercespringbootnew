package com.niladri.ecommerce.service.category;

import com.niladri.ecommerce.dto.category.CategoryPayloadDto;
import com.niladri.ecommerce.dto.category.CategoryResponseDto;
import com.niladri.ecommerce.exception.ApiException;
import com.niladri.ecommerce.exception.ResourceNotFound;
import com.niladri.ecommerce.model.category.CategoryModel;
import com.niladri.ecommerce.repository.category.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryServiceInterface {
    

    @Autowired
    private CategoryRepo categoryRepo;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryPayloadDto createCategory(String categoryName) {

        CategoryModel existingCategoryOrNot = categoryRepo.findByCategoryName(categoryName);
        if (existingCategoryOrNot != null) {
            throw new ApiException("Category already exists");
        }
        CategoryModel category = new CategoryModel();
        category.setCategoryName(categoryName);
        categoryRepo.save(category);
        return modelMapper.map(category, CategoryPayloadDto.class);
    }

    @Override
    public CategoryPayloadDto updateCategory(String categoryName, long categoryId) {
        Optional<CategoryModel> category = Optional.of
                (categoryRepo.findById(categoryId).orElseThrow(
                        () -> new ResourceNotFound("Category", "categoryId", categoryId)));
        category.get().setCategoryName(categoryName);
        category.get().setCategoryId(categoryId);
        CategoryModel updatedCategory  = categoryRepo.save(category.get());
        return modelMapper.map(updatedCategory, CategoryPayloadDto.class);
    }

    @Override
    public CategoryPayloadDto deleteCategory(long categoryId) {
       Optional<CategoryModel> category = Optional.of
               (categoryRepo.findById(categoryId).orElseThrow(
                       () -> new ResourceNotFound("Category", "categoryId", categoryId)));
         categoryRepo.delete(category.get());
         return modelMapper.map(category.get(), CategoryPayloadDto.class);
    }

    @Override
    public CategoryResponseDto getCategory(long categoryId) {
        Optional<CategoryModel> category = Optional.of
                (categoryRepo.findById(categoryId).orElseThrow(
                        () -> new ResourceNotFound("Category", "categoryId", categoryId)));
        return modelMapper.map(category.get(), CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto getCategories(Integer pageNo, Integer limit,String sortBy,String sortOrder) {
        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNo - 1, limit,sortByOrder);
        Page<CategoryModel> categoryPage = categoryRepo.findAll(pageDetails);
        List<CategoryModel> allCategories = categoryPage.getContent();
        if(allCategories.isEmpty()){
            throw new ApiException("No categories found");
        }
        List<CategoryPayloadDto> categoryPayloadDtos = allCategories.stream()
                .map(category -> modelMapper.map(category, CategoryPayloadDto.class)).toList();
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategories(categoryPayloadDtos);
        categoryResponseDto.setPageNo(pageNo);
        categoryResponseDto.setLimit(limit);
        categoryResponseDto.setTotalElements(categoryPage.getTotalElements());
        categoryResponseDto.setTotalPages(categoryPage.getTotalPages());
        categoryResponseDto.setIsLastPage(categoryPage.isLast());
        return categoryResponseDto;
    }
}
