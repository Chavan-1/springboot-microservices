package com.techm.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techm.entity.TransactionDetails;
import com.techm.model.PaymentRequest;
import com.techm.repository.TransactionDetailsRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private TransactionDetailsRepository transactionDetailsRepository;
	
	@Override
	public long doPayment(PaymentRequest paymentRequest) {
		log.info("Recording payment details: {}",paymentRequest);
		
		TransactionDetails transactionDetails = TransactionDetails.builder()
					                 .paymentDate(Instant.now())
					                 .paymentMode(paymentRequest.getPaymentMode().name())
					                 .paymentStatus("SUCCESS")
					                 .orderId(paymentRequest.getOrderId())
					                 .referenceNumber(paymentRequest.getReferenceNumber())
					                 .amount(paymentRequest.getAmount())
					                 .build();
		transactionDetailsRepository.save(transactionDetails);
		
		log.info("Transaction Completed with ID: {}", transactionDetails.getId());
		
		return transactionDetails.getId();
	}

}
