package org.billing.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class AuthResponse {

    private String role;
    private String email;
    private String token;
}
