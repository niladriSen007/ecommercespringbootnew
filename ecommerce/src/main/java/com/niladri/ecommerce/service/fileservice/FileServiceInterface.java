package com.niladri.ecommerce.service.fileservice;

import com.niladri.ecommerce.dto.product.ProductPayloadDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileServiceInterface {
    String uploadImage(String path, MultipartFile image) throws IOException;
}
