package com.techm.service;

import com.techm.model.PaymentRequest;

public interface PaymentService {

	long doPayment(PaymentRequest paymentRequest);

}
