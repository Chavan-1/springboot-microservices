package com.techm.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
	private long orderId;
	private Instant orderDate;
	private String orderStatus;
	private long amount;
	private ProductDetails productDetails;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class ProductDetails {
		private long productId;
		private String productName;
//		private long price;
		private long quantity;
	}
}
