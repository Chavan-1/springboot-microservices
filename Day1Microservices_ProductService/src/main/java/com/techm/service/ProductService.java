package com.techm.service;

import java.util.List;

import com.techm.model.ProductRequest;
import com.techm.model.ProductResponse;

public interface ProductService {

	long addProduct(ProductRequest productRequest);

	ProductResponse getProductById(long productId);

	void reduceQuantity(long productId, long quantity);

	List<ProductResponse> getAllProducts();

}
