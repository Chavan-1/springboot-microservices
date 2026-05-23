package com.techm.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techm.entity.Product;
import com.techm.exception.ProductServiceCustomException;
import com.techm.model.ProductRequest;
import com.techm.model.ProductResponse;
import com.techm.repository.ProductRepository;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public long addProduct(ProductRequest productRequest) {
		log.info("Adding Product......");
		Product product = Product.builder()
								 .productName(productRequest.getProductName())
								 .quantity(productRequest.getQuantity())
								 .price(productRequest.getPrice())
								 .build();
		productRepository.save(product);
		log.info("Product added successfully!");
		return product.getProductId();
	}
	
	@Override
	public ProductResponse getProductById(long productId) {
		
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductServiceCustomException("Product with given id does not exist", "PRODUCT_NOT_FOUND"));
		ProductResponse productResponse = new ProductResponse();
		BeanUtils.copyProperties(product, productResponse);
		log.info("Fetching Product with Product ID ", productId);
		return productResponse;
	}

	@Override
	public void reduceQuantity(long productId, long quantity) {
		log.info("Reduce Quantity {} for ID: {}", quantity, productId);
		
		Product product = productRepository.findById(productId)
						  .orElseThrow(() -> new ProductServiceCustomException("Product with given Id does not exist", "PRODUCT_NOT_FOUND"));
		
		if (product.getQuantity() < quantity) {
			throw new ProductServiceCustomException("Product does not have sufficient Quantity", "INSUFFICIANT_QUANTITY");
		}
		
		product.setQuantity(product.getQuantity() - quantity);
		
		productRepository.save(product);
		
		log.info("Product Quantity updated sucessfully!");
	}

	@Override
	public List<ProductResponse> getAllProducts() {
		
		List<Product> products = productRepository.findAll();

	    return products.stream()
	            .map(product -> ProductResponse.builder()
	                    .productId(product.getProductId())
	                    .productName(product.getProductName())
	                    .price(product.getPrice())
	                    .quantity(product.getQuantity())
	                    .build())
	            .collect(Collectors.toList());
	}

}
