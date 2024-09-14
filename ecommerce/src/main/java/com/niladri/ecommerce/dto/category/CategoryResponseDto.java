package com.niladri.ecommerce.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private List<CategoryPayloadDto> categories;
    private Integer pageNo;
    private Integer limit;
    private Long totalElements;
    private Integer totalPages;
    private Boolean isLastPage;
}
