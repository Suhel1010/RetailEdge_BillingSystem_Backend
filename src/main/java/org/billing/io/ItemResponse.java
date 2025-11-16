package org.billing.io;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private String itemId;
    private String description;
    private BigDecimal price;
    private String name;
    private String categoryName;
    private String categoryId;
    private String imgUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
