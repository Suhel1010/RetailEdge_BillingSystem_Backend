package org.billing.service;

import lombok.RequiredArgsConstructor;
import org.billing.entity.UserEntity;
import org.billing.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AppUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Finding user by email: " + email);

        UserEntity userEntity =  userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found with given email : "+email));
        System.out.println("User found: " + userEntity.getEmail());
        return  new User(userEntity.getEmail(),userEntity.getPassword(), Collections.singleton(new SimpleGrantedAuthority(userEntity.getRole())));
    }
}
