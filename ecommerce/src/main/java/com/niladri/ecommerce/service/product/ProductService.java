package com.niladri.ecommerce.service.product;

import com.niladri.ecommerce.dto.product.ProductPayloadDto;
import com.niladri.ecommerce.dto.product.ProductResponseDto;
import com.niladri.ecommerce.exception.ApiException;
import com.niladri.ecommerce.exception.ResourceNotFound;
import com.niladri.ecommerce.model.category.CategoryModel;
import com.niladri.ecommerce.model.product.ProductModel;
import com.niladri.ecommerce.repository.category.CategoryRepo;
import com.niladri.ecommerce.repository.product.ProductRepo;
import com.niladri.ecommerce.service.fileservice.FileService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceInterface {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path ;


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
        productModel.setProductSpecialPrice(productPayloadDto.getProductPrice() - (productPayloadDto.getProductPrice() * productPayloadDto.getProductDiscount() / 100));
        productModel.setCategory(category.get());
        ProductModel savedProduct =  productRepo.save(productModel);
        return modelMapper.map(savedProduct, ProductPayloadDto.class);
    }


    @Override
    public ProductResponseDto getAllProducts(Integer pageNo, Integer limit, String sortBy, String sortOrder) {
        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNo - 1, limit, sortByOrder);
        Page<ProductModel> productPage = productRepo.findAll(pageDetails);
        List<ProductModel> productsList = productPage.getContent();
        if (productsList.isEmpty()) {
            throw new ApiException("No products found");
        }

        return getProductResponseDto(pageNo, limit, productPage, productsList);
    }

    @Override
    public ProductResponseDto getAllProductsByCategory(Long categoryId, Integer pageNo,
                                                       Integer limit, String sortBy, String sortOrder) {
        Optional<CategoryModel> category = categoryRepo.findById(categoryId);
        if (category.isEmpty()) {
            throw new ResourceNotFound("Category", "categoryId", categoryId);
        }

        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNo - 1, limit, sortByOrder);
        Page<ProductModel> productPage = productRepo.findByCategory(category.get(), pageDetails);
        List<ProductModel> productsList = productPage.getContent();
        if (productsList.isEmpty()) {
            throw new ApiException(category.get().getCategoryName() + "has no products");
        }
        return getProductResponseDto(pageNo, limit, productPage, productsList);

    }

    @Override
    public ProductResponseDto getProductsByKeyword(String keyword, Integer pageNo, Integer limit, String sortBy, String sortOrder) {

        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNo - 1, limit, sortByOrder);
        Page<ProductModel> productPage = productRepo.findByProductNameLikeIgnoreCase("%"+keyword+"%", pageDetails);
        List<ProductModel> productsList = productPage.getContent();

        if (productsList.isEmpty()) {
            throw new ApiException("No products found with keyword: " + keyword);
        }

        return getProductResponseDto(pageNo, limit, productPage, productsList);
    }

    @Override
    public ProductPayloadDto updateProduct(ProductPayloadDto product, Long productId) {
        Optional<ProductModel> productModel = productRepo.findById(productId);
        if (productModel.isEmpty()) {
            throw new ResourceNotFound("Product", "productId", productId);
        }
        productModel.get().setProductName(product.getProductName());
        productModel.get().setProductDescription(product.getProductDescription());
        productModel.get().setProductPrice(product.getProductPrice());
        productModel.get().setProductQuantity(product.getProductQuantity());
        productModel.get().setProductDiscount(product.getProductDiscount());
        productModel.get().setProductSpecialPrice(product.getProductPrice() - (product.getProductPrice() * product.getProductDiscount() / 100));
        ProductModel updatedProduct = productRepo.save(productModel.get());
        return modelMapper.map(updatedProduct, ProductPayloadDto.class);
    }

    @Override
    public ProductPayloadDto deleteProduct(Long productId) {
        ProductModel productModel = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product", "productId", productId));
        productRepo.delete(productModel);
        return modelMapper.map(productModel, ProductPayloadDto.class);
    }

    @Override
    public ProductPayloadDto updateProductImage(MultipartFile image, Long productId) throws IOException {
        Optional<ProductModel> product = productRepo.findById(productId);
        if (product.isEmpty()) {
            throw new ResourceNotFound("Product", "productId", productId);
        }

        // Code to update image
//        String path = "images/";
        String fileName = fileService.uploadImage(path,image);

        product.get().setProductImage(fileName);
        ProductModel updatedProduct = productRepo.save(product.get());
        return modelMapper.map(updatedProduct, ProductPayloadDto.class);

    }



    private ProductResponseDto getProductResponseDto(Integer pageNo, Integer limit, Page<ProductModel> productPage, List<ProductModel> productsList) {
        List<ProductPayloadDto> productPayloadDtoList = productsList.stream()
                .map(productModel -> modelMapper.map(productModel, ProductPayloadDto.class))
                .toList();

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setProducts(productPayloadDtoList);
        productResponseDto.setPageNo(pageNo);
        productResponseDto.setLimit(limit);
        productResponseDto.setTotalElements(productPage.getTotalElements());
        productResponseDto.setTotalPages(productPage.getTotalPages());
        productResponseDto.setIsLastPage(productPage.isLast());
        return productResponseDto;
    }
}
