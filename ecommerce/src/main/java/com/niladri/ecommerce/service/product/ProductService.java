package com.niladri.ecommerce.service.product;

import com.niladri.ecommerce.dto.product.ProductPayloadDto;
import com.niladri.ecommerce.exception.ApiException;
import com.niladri.ecommerce.exception.ResourceNotFound;
import com.niladri.ecommerce.model.category.CategoryModel;
import com.niladri.ecommerce.model.product.ProductModel;
import com.niladri.ecommerce.repository.category.CategoryRepo;
import com.niladri.ecommerce.repository.product.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements ProductServiceInterface {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public ProductPayloadDto createProduct(ProductPayloadDto productPayloadDto, Long categoryId) {
        Optional<CategoryModel> category = categoryRepo.findById(categoryId);
        if (category.isEmpty()) {
            throw new ResourceNotFound("Category", "categoryId", categoryId);
        }
        ProductModel product = productRepo.findByProductName(productPayloadDto.getProductName());
        if (product != null) {
            throw new ApiException("Product already exists");
        }
        ProductModel productModel = new ProductModel();
        productModel.setProductName(productPayloadDto.getProductName());
        productModel.setProductDescription(productPayloadDto.getProductDescription());
        productModel.setProductPrice(productPayloadDto.getProductPrice());
        productModel.setProductQuantity(productPayloadDto.getProductQuantity());
        productModel.setProductDiscount(productPayloadDto.getProductDiscount());
        productModel.setProductImage(productPayloadDto.getProductImage());
        productModel.setProductSpecialPrice(
                productPayloadDto.getProductPrice() -
                        (productPayloadDto.getProductPrice() * productPayloadDto.getProductDiscount() / 100));
        productModel.setCategory(category.get());
        productRepo.save(productModel);
        return modelMapper.map(productModel, ProductPayloadDto.class);
    }
}
