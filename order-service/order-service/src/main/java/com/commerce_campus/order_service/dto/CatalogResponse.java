package com.commerce_campus.order_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CatalogResponse {
    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String status;
}
