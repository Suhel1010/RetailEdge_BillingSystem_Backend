package org.billing.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private String orderId;
    private String customerName;
    private String phoneNumber;
    private Double subTotal;
    private Double grandTotal;
    private Double tax;
    private List<OrderResponse.OrderItemResponse> items;
    private PaymentDetails paymentDetails;
    private LocalDateTime createdAt;
    private PaymentMethod paymentMethod;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderItemResponse{
        private String itemId;
        private String name;
        private Double price;
        private Integer quantity;

    }
}
