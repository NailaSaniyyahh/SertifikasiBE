package com.commerce_campus.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//data yg dikirim oleh user
public class OrderItemRequest {
    @NotNull(message = "product id tidak boleh kosong")
    private Long productId;

    @NotNull(message = "items tidak boleh kosong")
    @Min(value = 1, message = "quantity minimal 1")
    private Integer quantity;
}
