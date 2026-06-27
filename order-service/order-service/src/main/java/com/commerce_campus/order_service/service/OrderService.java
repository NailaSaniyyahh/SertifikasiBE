package com.commerce_campus.order_service.service;

import com.commerce_campus.order_service.client.CatalogClient;
import com.commerce_campus.order_service.dto.CatalogResponse;
import com.commerce_campus.order_service.dto.OrderItemRequest;
import com.commerce_campus.order_service.dto.OrderRequest;
import com.commerce_campus.order_service.dto.OrderResponse;
import com.commerce_campus.order_service.model.Order;
import com.commerce_campus.order_service.model.OrderItem;
import com.commerce_campus.order_service.model.OrderStatus;
import com.commerce_campus.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final CatalogClient catalogClient;

    public OrderService(OrderRepository orderRepository, CatalogClient catalogClient) {
        this.orderRepository = orderRepository;
        this.catalogClient = catalogClient;
    }

    private Order findOrder(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("order " + id + "tidak ditemukan"));
    }

    public OrderResponse create(OrderRequest request){
        Order order = new Order();
        order.setCustomerName(request.getCustomerName().trim());
        order.setCustomerEmail(request.getCustomerEmail().trim());

        List<OrderItem> items = new ArrayList<>();
        BigDecimal hargaTotal = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            CatalogResponse catalog = catalogClient.getProduct(itemRequest.getProductId());
//            product inactive tdk boleh dipesan
            if ("INACTIVE".equals(catalog.getStatus())) {
                throw new RuntimeException("produk '" + catalog.getName() + "' inactive dan tidak bisa dipesan");
            }

//            cek stock cukup ap aga
            if (catalog.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Stok produk '" + catalog.getName() + "' tidak cukup. ");
            }
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(catalog.getId());
            item.setProductName(catalog.getName());
            item.setProductPrice(catalog.getPrice());
            item.setQuantity(itemRequest.getQuantity());
            item.setTotal(catalog.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));

            items.add(item);
            hargaTotal = hargaTotal.add(item.getTotal());

            catalogClient.updateStock(catalog.getId(), -itemRequest.getQuantity());
        }

        order.setItems(items);
        order.setHargaTotal(hargaTotal);

        return OrderResponse.from(orderRepository.save(order));
    }

    //    daftar order
    @Transactional(readOnly = true)
    public List<OrderResponse> findAll(){
        return orderRepository.findAll().stream().map(OrderResponse::from).toList();
    }

//    detail order
    @Transactional(readOnly = true)
    public OrderResponse findById(Long id){
        return OrderResponse.from(findOrder(id));
    }

//    pay order = pending-paid
    public OrderResponse pay(Long id) {
        Order order = findOrder(id);
//        hanya PENDING yang bisa dibayar
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException(
                    "tidak bisa dibayar karena status " + order.getStatus().name() + ", Hanya PENDING yang bisa dibayar/dibatalkan"
            );
        }
        order.setStatus(OrderStatus.PAID);
        return OrderResponse.from(orderRepository.save(order));
    }

    //    cancel order = pending-cancelled
    public OrderResponse cancel(Long id) {
        Order order = findOrder(id);
//        hanya PENDING yang bisa dibatalkan
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException(
                    "tidak bisa dicancel karena status " + order.getStatus().name() + ", Hanya PENDING yang bisa dibayar/dibatalkan"
            );
        }

//        kembalikan stok ke catalog-service
        for (OrderItem item : order.getItems()) {
            catalogClient.updateStock(item.getProductId(), item.getQuantity()); // positif = kembalikan
        }

        order.setStatus(OrderStatus.CANCELLED);
        return OrderResponse.from(orderRepository.save(order));
    }
}
