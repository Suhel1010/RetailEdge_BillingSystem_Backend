package org.billing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.billing.entity.ItemEntity;
import org.billing.io.ItemRequest;
import org.billing.io.ItemResponse;
import org.billing.repository.ItemRepository;
import org.billing.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;


    @PostMapping("/admin/items")
    public ItemResponse addItem(@RequestPart("item") String itemString,
                                @RequestPart("file") MultipartFile file){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            ItemRequest  itemRequest = objectMapper.readValue(itemString,ItemRequest.class);
            return itemService.add(itemRequest,file);
        }catch (JsonProcessingException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error while process the json ");
        }

    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemResponse>> readItem(){
        List<ItemResponse> fetched = itemService.fetchItem();
        return ResponseEntity.ok(fetched);
    }

    @DeleteMapping("/admin/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable String itemId){
        ItemEntity response = itemRepository.findByItemId(itemId)
                .orElseThrow(()-> new RuntimeException("item not found with given id = "+itemId));
        if (response != null)
            itemService.deleteItem(itemId);
         else
            throw new RuntimeException("Item is not delete !");
    }

}
