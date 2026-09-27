package com.amazon.order.dto;

public class OrderResponse {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;

    public OrderResponse(Long id, Long userId, Long productId, Integer quantity) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
}
