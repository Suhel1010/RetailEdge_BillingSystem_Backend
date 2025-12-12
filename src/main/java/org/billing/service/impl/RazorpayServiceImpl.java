package org.billing.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.billing.io.RazorpayOrderResponse;
import org.billing.service.RazorpayService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RazorpayServiceImpl implements RazorpayService {

    @Value("${razorpay_key_id}")
    private String razorpayKeyId;
    @Value("${razorpay_key_secret}")
    private String razorpaySecretId;




    @Override
    public RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId,razorpaySecretId);
        JSONObject orderRequest = new JSONObject();
        //convert to integer into paise
        int amountInPaise = (int) Math.round(amount * 100);
        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", "order_receipt" + System.currentTimeMillis());
        orderRequest.put("payment_capture",1);
        Order order = razorpayClient.orders.create(orderRequest);
        return convertToResponse(order);
    }

    private RazorpayOrderResponse convertToResponse(Order order) {
       return RazorpayOrderResponse.builder()
               .id(order.get("id"))
               .entity(order.get("entity"))
               .amount(order.get("amount"))
               .currency(order.get("currency"))
               .status(order.get("status"))
               .created_at(order.get("created_at"))
               .receipt(order.get("receipt"))
               .build();
    }


}
