package org.billing.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.billing.io.UserRequest;
import org.billing.io.UserResponse;
import org.billing.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse createUser(@RequestBody UserRequest userRequest){
        try{
            return userService.createUser(userRequest);
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to create ");
        }
    }

    @GetMapping
    public List<UserResponse> getUser(){
        return userService.readUsesr();
    }


    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable String id){
        try{
            userService.deleteUser(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("getRole")
    public String getRole(@RequestBody String email){
        return userService.getUserRole(email);
    }




}
