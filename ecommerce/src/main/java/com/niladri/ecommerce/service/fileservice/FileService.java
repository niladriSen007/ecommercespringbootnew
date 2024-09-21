package com.niladri.ecommerce.service.fileservice;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService implements FileServiceInterface {
    public String uploadImage(String path, MultipartFile image) throws IOException {
        //Get the file names
        String orginalFileName = image.getOriginalFilename();

        //generate a unique file name
        String randomId = UUID.randomUUID().toString();
        String newUniqueFileName =randomId.concat(orginalFileName.substring(orginalFileName.lastIndexOf(".")));
        String fullPath = path + File.separator + newUniqueFileName;


        //check if the path exists if not create the path
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
        //upload to server
        Files.copy((image.getInputStream()), Paths.get(fullPath));

        //return the file name
        return newUniqueFileName;


    }
}
