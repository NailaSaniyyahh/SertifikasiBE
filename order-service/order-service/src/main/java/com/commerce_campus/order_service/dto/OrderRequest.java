package com.commerce_campus.order_service.dto;

import com.commerce_campus.order_service.model.OrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//data yg dikirim oleh user
public class OrderRequest {
    @NotBlank(message = "customer name tidak boleh kosong")
    private String customerName;

    @NotBlank(message = "customer email tidak boleh kosong")
    @Email(message = "format email harus valid")
    private String customerEmail;

    @NotEmpty(message = "items minimal 1")
    @Valid
    private List<OrderItemRequest> items;
}
