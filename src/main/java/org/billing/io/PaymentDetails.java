package org.billing.io;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDetails {

    private String razorPayOrderId;
    private String razorPayPaymentId;
    private String razorPaySignature;
    private PaymentStatus paymentStatus;
    public enum PaymentStatus{
        Pending,Completed,Failed;
    }

}
