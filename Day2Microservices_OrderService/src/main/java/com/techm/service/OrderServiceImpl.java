package com.techm.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.techm.entity.Order;
import com.techm.exception.CustomException;
import com.techm.external.client.PaymentService;
import com.techm.external.client.ProductService;
import com.techm.external.model.PaymentRequest;
import com.techm.external.model.ProductResponse;
import com.techm.model.OrderRequest;
import com.techm.model.OrderResponse;
import com.techm.repository.OrderRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public long placeOrder(OrderRequest orderRequest) {
		
		log.info("Placing Order Request: {}", orderRequest);
		
		//Invoking Product Service to reduce quantity
		productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
		
		log.info("Creating Order with status CREATED");
		
		Order order = Order
				.builder()
				.amount(orderRequest.getTotalAmount())
				.productId(orderRequest.getProductId())
				.orderDate(Instant.now())
				.quantity(orderRequest.getQuantity())
				.build();
		
		order = orderRepository.save(order);
		
		log.info("Calling payment service ");
		
		PaymentRequest paymentRequest = PaymentRequest.builder()
									    .orderId(order.getId())
									    .paymentMode(orderRequest.getPaymentMode())
									    .amount(orderRequest.getTotalAmount())
									    .build();
		
		String orderStatus =  null;
		try {
			paymentService.doPayment(paymentRequest);
			log.info("Payment done successfully. Changing the OrderStatus to PLACED");
			orderStatus = "PLACED";
		} catch (Exception e) {
			log.info("Error ocurred in Payment. Changing the OrderStatus to PAYMENT_FAILED");
			orderStatus = "PAYMENT_FAILED";
		}
		
		order.setOrderStatus(orderStatus);
		orderRepository.save(order);
		
		log.info("Order placed successfully with order ID: {}", order.getId());
		
		return order.getId();
	}

	@Override
	public OrderResponse getOrderDetails(long orderId) {
		log.info("Get Order details for order ID: {}", orderId);
		
		Order order = orderRepository.findById(orderId)
									 .orElseThrow(() -> new CustomException("Order not found for the order ID: "+ orderId, "NOT_FOUND")); 
		
		log.info("Invoking product service to fetch the product for id: {}", order.getProductId());
		
		ProductResponse productResponse = restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+ order.getProductId(), ProductResponse.class);
		
		//Payment Service - using webClient
		
		
		OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails
																 .builder()
															     .productName(productResponse.getProductName())
															     .productId(productResponse.getProductId())
											//				     .quantity(productResponse.getQuantity())
															     .build();

		OrderResponse orderResponse = OrderResponse.builder()
					                  .orderId(order.getId())
					                  .orderStatus(order.getOrderStatus())
					                  .amount(order.getAmount())
					                  .orderDate(order.getOrderDate())
					                  .productDetails(productDetails)
					                  .build();
		return orderResponse;
	}
	
	@Autowired
    private ProductServiceWebClient productServiceWebClient;

	    public void testProducts() {
	        List<ProductResponse> products = productServiceWebClient.getAllProducts();
	
	        products.forEach(System.out::println);
	    }
	
}
