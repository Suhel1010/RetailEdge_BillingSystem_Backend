package org.billing.service;

import com.razorpay.RazorpayException;
import org.billing.io.RazorpayOrderResponse;

public interface RazorpayService {

    RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;
}
