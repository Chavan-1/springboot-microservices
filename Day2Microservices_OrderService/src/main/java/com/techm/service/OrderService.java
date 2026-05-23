package com.techm.service;

import com.techm.model.OrderRequest;
import com.techm.model.OrderResponse;

public interface OrderService {

	long placeOrder(OrderRequest orderRequest);

	OrderResponse getOrderDetails(long orderId);

}
