package com.amazon.order.client;

import com.amazon.order.dto.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public ProductResponse getProductById(Long id) {
        return new ProductResponse(id, "Product service unavailable", null);
    }
}