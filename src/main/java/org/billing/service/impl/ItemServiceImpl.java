package org.billing.service.impl;

import lombok.RequiredArgsConstructor;
import org.billing.entity.CategoryEntity;
import org.billing.entity.ItemEntity;
import org.billing.io.ItemRequest;
import org.billing.io.ItemResponse;
import org.billing.repository.CategoryRepository;
import org.billing.repository.ItemRepository;
import org.billing.service.FileUploadService;
import org.billing.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;

    @Override
    public ItemResponse add(ItemRequest itemRequest, MultipartFile file) {
        String imgUrl = fileUploadService.uploadFile(file);
        CategoryEntity categoryEntity = categoryRepository.findByCategoryId(itemRequest.getCategoryId())
                .orElseThrow(()-> new RuntimeException("category not found with given categoryId = "+itemRequest.getCategoryId()));

        ItemEntity itemEntity = ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(itemRequest.getName())
                .description(itemRequest.getDescription())
                .price(itemRequest.getPrice())
                .categoryEntity(categoryEntity)
                .imgUrl(imgUrl)
                .build();
        ItemEntity saved = itemRepository.save(itemEntity);
        return ItemResponse.builder()
                .itemId(saved.getItemId())
                .description(saved.getDescription())
                .price(saved.getPrice())
                .name(saved.getName())
                .categoryName(saved.getCategoryEntity().getName())
                .categoryId(saved.getCategoryEntity().getCategoryId())
                .imgUrl(saved.getImgUrl())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();

    }

    @Override
    public List<ItemResponse> fetchItem() {
        return itemRepository.findAll()
                .stream().map(itemEntity -> ItemResponse.builder()
                        .itemId(itemEntity.getItemId())
                        .description(itemEntity.getDescription())
                        .price(itemEntity.getPrice())
                        .name(itemEntity.getName())
                        .categoryName(itemEntity.getCategoryEntity().getName())
                        .categoryId(itemEntity.getCategoryEntity().getCategoryId())
                        .imgUrl(itemEntity.getImgUrl())
                        .createdAt(itemEntity.getCreatedAt())
                        .updatedAt(itemEntity.getUpdatedAt())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override

    public void deleteItem(String itemId) {
        ItemEntity item = itemRepository.findByItemId(itemId)
                .orElseThrow(()-> new RuntimeException("item not found given id = "+itemId));
        boolean isDeleted = fileUploadService.deleteFile(item.getImgUrl());
        if(isDeleted){
            itemRepository.delete(item);
        }else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to delete category ");
        }

    }
}
