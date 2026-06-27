package com.commerce_campus.catalog_service.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
//data yg dikirim oleh user
public class CatalogRequest{
    @NotBlank(message = "SKU tidak boleh kosong")
    private String sku;

    @NotBlank(message = "Nama wajib diisi")
    private String name;

    @Positive(message = "price harus > 0")
    private BigDecimal price;

    @PositiveOrZero(message = "Stock minimal 0 (tidak boleh negatif)")
    private Integer stock;
}
