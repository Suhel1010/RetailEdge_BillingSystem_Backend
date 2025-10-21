package org.billing.service;

import org.billing.io.CategoryRequest;
import org.billing.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    CategoryResponse add(CategoryRequest categoryRequest, MultipartFile multipartFile);

    List<CategoryResponse> read();

    void destroy(String categoryId);
}
