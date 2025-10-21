package org.billing.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String uploadFile(MultipartFile file);
    boolean deleteFile(String imgURL);

}
