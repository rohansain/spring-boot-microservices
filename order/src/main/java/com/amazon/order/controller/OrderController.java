package com.amazon.order.controller;


import com.amazon.order.dto.OrderRequest;
import com.amazon.order.dto.OrderResponse;
import com.amazon.order.dto.ProductResponse;
import com.amazon.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.amazon.order.client.ProductClient;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final ProductClient productClient;


    public OrderController(OrderService orderService, ProductClient productClient) {
        this.orderService = orderService;
        this.productClient = productClient;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/product/{id}")
    public ProductResponse getProductForOrder(@PathVariable Long id) {

        return productClient.getProductById(id);
    }
}