package org.billing.io;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String email;
    private String password;
    private String role;
    private String name;

}
