package org.billing.service;

import org.billing.io.UserRequest;
import org.billing.io.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);
    String getUserRole(String email);
    List<UserResponse> readUsesr();
    void deleteUser(String userId);

}
