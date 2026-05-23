package com.techm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.techm.external.model.ProductResponse;

@Service
public class ProductServiceWebClient {
	
	 @Autowired
	 private WebClient webClient;
	
	public List<ProductResponse> getAllProducts() {

        return webClient
                .get()
                .uri("/product")
                .retrieve()
                .bodyToFlux(ProductResponse.class)
                .collectList()
                .block();
    }	
}
