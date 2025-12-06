package org.billing.service.impl;

import lombok.RequiredArgsConstructor;
import org.billing.entity.UserEntity;
import org.billing.io.UserRequest;
import org.billing.io.UserResponse;
import org.billing.repository.UserRepository;
import org.billing.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()){
            throw new RuntimeException("Email already exist !!" +userRequest.getEmail());
        }
        UserEntity userEntity = UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(userRequest.getRole())
                .build();
        UserEntity saved = userRepository.save(userEntity);
        return UserResponse.builder()
                .userId(saved.getUserId())
                .name(saved.getName())
                .email(saved.getEmail())
                .role(saved.getRole())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }

    @Override
    public String getUserRole(String email) {
       UserEntity user =  userRepository.findByEmail(email)
               .orElseThrow(()->new UsernameNotFoundException("User not found with this email !"+email));
       return user.getRole();
    }

    @Override
    public List<UserResponse> readUsesr() {
        return userRepository.findAll().stream()
                .map(user -> UserResponse.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .userId(user.getUserId())
                        .createdAt(user.getCreatedAt())
                        .updatedAt(user.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser( String  userId) {
        UserEntity response = userRepository.findByUserId(userId)
                .orElseThrow(()-> new UsernameNotFoundException("User not found !!"));
        userRepository.delete(response);

    }

}
