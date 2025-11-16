package org.billing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.billing.Uitl.JwtUtil;
import org.billing.io.AuthRequest;
import org.billing.io.AuthResponse;
import org.billing.service.AppUserDetailService;
import org.billing.service.UserService;
import org.billing.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailService appUserDetailService;
    private final JwtUtil jwtUtil;
    private final UserServiceImpl userServiceIml;

    @PostMapping("/encode")
    private String passwordEncoder(@RequestBody Map<String,String> request){
        return passwordEncoder.encode(request.get("password"));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception{
        authenticat(authRequest.getEmail(),authRequest.getPassword());
        final UserDetails userDetails =  appUserDetailService.loadUserByUsername(authRequest.getEmail());
        String jwtToken =  jwtUtil.generateToken(userDetails);
        String role  = userServiceIml.getUserRole(authRequest.getEmail());
        return new AuthResponse(role,authRequest.getEmail(),jwtToken);
    }

    private void authenticat(String email,String password){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        } catch (DisabledException a) {
            throw new RuntimeException("User Disable"  );
        } catch (BadCredentialsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email or password is incorrect");
        }

    }
}
