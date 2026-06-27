package com.commerce_campus.order_service.dto;

import com.commerce_campus.order_service.model.Order;
import com.commerce_campus.order_service.model.OrderItem;
import com.commerce_campus.order_service.model.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private String customerName;
    private String customerEmail;
    private BigDecimal hargaTotal;
    private Instant createdAt;
    private Instant updatedAt;
    private OrderStatus status;
    private List<OrderItemResponse> items;

    //    konversi dari entity ke dto
    public static OrderResponse from (Order order) {
        OrderResponse response = new OrderResponse();
        response.id = order.getId();
        response.customerName = order.getCustomerName();
        response.customerEmail = order.getCustomerEmail();
        response.hargaTotal = order.getHargaTotal();
        response.createdAt = order.getCreatedAt();
        response.updatedAt = order.getUpdatedAt();
        response.status = order.getStatus();
        response.items = order.getItems().stream().map(OrderItemResponse::from).toList();

        return response;
    }
}
