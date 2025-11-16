package org.billing.service.impl;

import lombok.RequiredArgsConstructor;
import org.billing.entity.CategoryEntity;
import org.billing.io.CategoryRequest;
import org.billing.io.CategoryResponse;
import org.billing.repository.CategoryRepository;
import org.billing.repository.ItemRepository;
import org.billing.service.CategoryService;
import org.billing.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;
    private final ItemRepository itemRepository;

    @Override
    public CategoryResponse add(CategoryRequest categoryRequest, MultipartFile multipartFile) {
        String imgUrl = fileUploadService.uploadFile(multipartFile);
        CategoryEntity newCategory = convertToEntity(categoryRequest);
        newCategory.setImgUrl(imgUrl);
        newCategory = categoryRepository.save(newCategory);
        return convertToResponse(newCategory);

    }

    @Override
    public List<CategoryResponse> read() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryEntity -> convertToResponse(categoryEntity))
                .collect(Collectors.toList());

    }

    @Override
    public void destroy(String categoryId) {
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(()-> new RuntimeException("Category not found"));
        fileUploadService.deleteFile(existingCategory.getImgUrl());
        categoryRepository.delete(existingCategory);

    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        Integer itemCount = itemRepository.countByCategoryEntity_Id(newCategory.getId());
        return CategoryResponse.builder()
                .CategoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .imgUrl(newCategory.getImgUrl())
                .items(itemCount)
                .createdAt(String.valueOf(newCategory.getCreatedAt()))
                .updatedAt(String.valueOf(newCategory.getUpdatedAt()))
                .build();
    }

    private CategoryEntity convertToEntity(CategoryRequest categoryRequest) {
       return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .bgColor(categoryRequest.getBgColor())
                .build();
    }
}
