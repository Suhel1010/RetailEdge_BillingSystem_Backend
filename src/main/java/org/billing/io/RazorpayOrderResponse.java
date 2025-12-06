package org.billing.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RazorpayOrderResponse {

    private String id;
    private String entity;
    private Integer amount;
    private String currency;
    private String Status;
    private Data createdAt;
    private String receipt;

}

