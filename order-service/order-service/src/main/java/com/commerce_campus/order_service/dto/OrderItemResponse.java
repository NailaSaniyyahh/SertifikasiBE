package com.commerce_campus.order_service.dto;

import com.commerce_campus.order_service.model.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
//data yg ditampilkan ke user
public class OrderItemResponse {
    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity;
    private BigDecimal total;

//    konversi dari entity ke dto
    public static OrderItemResponse from (OrderItem items) {
        OrderItemResponse response = new OrderItemResponse();
        response.id = items.getId();
        response.productId = items.getProductId();
        response.productName = items.getProductName();
        response.productPrice = items.getProductPrice();
        response.quantity = items.getQuantity();
        response.total = items.getTotal();
        return response;
    }
}
