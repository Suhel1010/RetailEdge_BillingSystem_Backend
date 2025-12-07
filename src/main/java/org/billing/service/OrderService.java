package org.billing.service;

import org.billing.io.OrderRequest;
import org.billing.io.OrderResponse;
import org.billing.io.PaymentVarificationRequest;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest  request);
    void deleteOrder(String orderId);
    List<OrderResponse> getLatestOrders();
    OrderResponse varifyPayment(PaymentVarificationRequest request);
}
