package com.commerce_campus.catalog_service.dto;

import com.commerce_campus.catalog_service.model.Catalog;
import com.commerce_campus.catalog_service.model.CatalogStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

//data yg ditampilkan ke user
@Getter
@Setter
public class CatalogResponse {
    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private CatalogStatus status;
    private Instant createdAt;
    private Instant updatedAt;

//    konversi dari entity ke dto
    public static CatalogResponse from (Catalog catalog) {
        CatalogResponse response = new CatalogResponse();
        response.id = catalog.getId();
        response.sku = catalog.getSku();
        response.name = catalog.getName();
        response.price = catalog.getPrice();
        response.stock = catalog.getStock();
        response.status = catalog.getStatus();
        response.createdAt = catalog.getCreatedAt();
        response.updatedAt = catalog.getUpdatedAt();
        return response;
    }
}