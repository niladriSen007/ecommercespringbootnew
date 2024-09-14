package com.niladri.ecommerce.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private List<ProductPayloadDto> products;
//    private Integer pageNo;
//    private Integer limit;
//    private Long totalElements;
//    private Integer totalPages;
//    private Boolean isLastPage;

}
