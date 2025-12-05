package org.billing.service;

import com.razorpay.RazorpayException;
import org.billing.io.RazorpayOrderResponse;

public interface RazorpayService {

    RazorpayOrderResponse craeteOrder(Double amount, String currency) throws RazorpayException;
}
