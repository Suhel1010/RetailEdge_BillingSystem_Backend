package org.billing.io;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryResponse {
    private String CategoryId;
    private String name;
    private String description;
    private String bgColor;
    private String createdAt;
    private String updatedAt;
    private String imgUrl;
}
