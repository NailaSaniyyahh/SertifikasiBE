package com.commerce_campus.order_service.controller;

import com.commerce_campus.order_service.dto.OrderRequest;
import com.commerce_campus.order_service.dto.OrderResponse;
import com.commerce_campus.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

//    post /api/orders
    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

//    get /api/orders
    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll(){
        return ResponseEntity.ok(
                orderService.findAll()
        );
    }

//    get /api/orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                orderService.findById(id)
        );
    }

//    patch bayar order -/api/orders/{id}/pay
    @PatchMapping("/{id}/pay")
    public ResponseEntity<OrderResponse> pay(@PathVariable Long id){
        return ResponseEntity.ok(orderService.pay(id));
    }

//    patch cancel order -/api/orders/{id}/cancel
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancel(@PathVariable Long id){
        return ResponseEntity.ok(orderService.cancel(id));
    }
}
