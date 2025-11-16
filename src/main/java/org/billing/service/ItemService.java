package org.billing.service;

import org.billing.io.ItemRequest;
import org.billing.io.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    ItemResponse add(ItemRequest itemRequest, MultipartFile file);
    List<ItemResponse> fetchItem();
    void deleteItem(String itemId);
}
