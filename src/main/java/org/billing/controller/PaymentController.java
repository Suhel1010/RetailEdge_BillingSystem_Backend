package org.billing.controller;

import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.billing.io.OrderResponse;
import org.billing.io.PaymentRequest;
import org.billing.io.PaymentVarificationRequest;
import org.billing.io.RazorpayOrderResponse;
import org.billing.service.OrderService;
import org.billing.service.RazorpayService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final RazorpayService razorpayService;
    private final OrderService orderService;

    @PostMapping("/create-order")
    @ResponseStatus(HttpStatus.CREATED)
    public RazorpayOrderResponse createRazorpayOrder(@RequestBody PaymentRequest request) throws RazorpayException {
        return razorpayService.createOrder(request.getAmount(),request.getCurrency());
    }

    @PostMapping("/verify")
    public OrderResponse varifyPayment(@RequestBody PaymentVarificationRequest request){
        return orderService.varifyPayment(request);
    }



}

