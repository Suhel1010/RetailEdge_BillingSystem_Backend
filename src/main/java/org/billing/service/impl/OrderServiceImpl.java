package org.billing.service.impl;

import lombok.RequiredArgsConstructor;
import org.billing.entity.OrderEntity;
import org.billing.entity.OrderItemEntity;
import org.billing.io.*;
import org.billing.repository.OrderEntityRepository;
import org.billing.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.billing.io.PaymentMethod.CASH;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderEntityRepository orderEntityRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        OrderEntity newOrder = convertToOrderEntity(request);
        if(request.getCartItems() == null || request.getCartItems().isEmpty()){
            throw new RuntimeException("Cart items can not be empty !!");
        }
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPaymentStatus(newOrder.getPaymentMethod() == CASH ? PaymentDetails.PaymentStatus.Completed : PaymentDetails.PaymentStatus.Pending);
        newOrder.setPaymentDetails(paymentDetails);
        List <OrderItemEntity> orderResponses = request.getCartItems()
                .stream().map(this::convertToOrderEntity).collect(Collectors.toList());
        newOrder.setItems(orderResponses);
        newOrder = orderEntityRepository.save(newOrder);
        return convertToResponse(newOrder);
    }

    private OrderResponse convertToResponse(OrderEntity newOrder) {
         return OrderResponse.builder()
                 .orderId(newOrder.getOrderId())
                 .customerName(newOrder.getCustomerName())
                 .phoneNumber(newOrder.getPhoneNumber())
                 .subTotal(newOrder.getSubTotal())
                 .tax(newOrder.getTax())
                 .grandTotal(newOrder.getGrandTotal())
                 .paymentMethod(newOrder.getPaymentMethod())
                 .items(newOrder.getItems().stream().map(this::convertToItemRequest).collect(Collectors.toList()))
                 .paymentDetails(newOrder.getPaymentDetails())
                 .createdAt(newOrder.getCreatedAt())
                 .build();

    }

    private OrderResponse.OrderItemResponse convertToItemRequest(OrderItemEntity orderItemEntity) {
        return OrderResponse.OrderItemResponse.builder()
                .itemId(orderItemEntity.getItemId())
                .name(orderItemEntity.getName())
                .price(orderItemEntity.getPrice())
                .quantity(orderItemEntity.getQuantity())
                .build();
    }

    private OrderEntity convertToOrderEntity(OrderRequest request) {
        return OrderEntity.builder()
                .customerName(request.getCustomerName())
                .phoneNumber(request.getPhoneNumber())
                .subTotal(request.getSubTotal())
                .tax(request.getTax())
                .grandTotal(request.getGrandTotal())
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .build();
    }
    private OrderItemEntity convertToOrderEntity(OrderRequest.OrderItemRequest orderItemRequest) {
        return OrderItemEntity.builder()
                .itemId(orderItemRequest.getItemId())
                .name(orderItemRequest.getName())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .build();
    }

    @Override
    public void deleteOrder(String orderId) {
        OrderEntity existingOrder = orderEntityRepository.findByOrderId(orderId)
                .orElseThrow(()->new RuntimeException("Order not found !!"));
        orderEntityRepository.delete(existingOrder);

    }

    @Override
    public List<OrderResponse> getLatestOrders() {
        return orderEntityRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse varifyPayment(PaymentVarificationRequest request) {
        OrderEntity existingOrder = orderEntityRepository.findByOrderId(request.getOrderId())
                .orElseThrow(()-> new RuntimeException("Order not found !!") );
        if(!verifyRazorpaySignature(request.getRazorpayPaymentId(),
                request.getRazorpayPaymentId(),
                request.getRazorpaySignature())) {
            throw new RuntimeException("Payment varification failed !");
        }

        PaymentDetails paymentDetails = existingOrder.getPaymentDetails();
        paymentDetails.setRazorpayOrderId(request.getRazorpayOrderId());
        paymentDetails.setRazorpayPaymentId(request.getRazorpayPaymentId());
        paymentDetails.setRazorpaySignature(request.getRazorpaySignature());
        paymentDetails.setPaymentStatus(PaymentDetails.PaymentStatus.Completed);
        existingOrder = orderEntityRepository.save(existingOrder);
        return convertToResponse(existingOrder);
    }

    @Override
    public Double sumSalesByDate(LocalDate date) {
        return orderEntityRepository.sumSalesByDate(date);
    }

    @Override
    public Long countByOrderDate(LocalDate date) {
        return orderEntityRepository.countByOrderDate(date);
    }

    @Override
    public List<OrderResponse> findRecentOrders() {
        return orderEntityRepository.findRecentOrders(PageRequest.of(0,10))
                .stream().map(orderEntity -> convertToResponse(orderEntity))
                .collect(Collectors.toList());
    }

    private boolean verifyRazorpaySignature(String razorpayPaymentId, String razorpayOrderId, String razorPaySignature) {
        return true;
    }


}
