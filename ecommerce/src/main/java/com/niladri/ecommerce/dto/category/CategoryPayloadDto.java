package com.niladri.ecommerce.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPayloadDto {
    private long categoryId;
    private String categoryName;
}
