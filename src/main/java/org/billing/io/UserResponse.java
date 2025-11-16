package org.billing.io;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String userId;
    private String email;
    private String role;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
