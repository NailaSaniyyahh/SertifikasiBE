package com.commerce_campus.order_service.client;

import com.commerce_campus.order_service.dto.CatalogResponse;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class CatalogClient {

    private final RestClient restClient;

    public CatalogClient(@Value("${catalog.service.url}") String catalogServiceUrl){
        this.restClient = RestClient.builder()
                .baseUrl(catalogServiceUrl)
                .build();
    }

//    ambil data produk dari catalog service
    public CatalogResponse getProduct(Long productId) {
        try {
            return restClient.get()
                    .uri("/api/products/{id}", productId)
                    .retrieve()
                    .body(CatalogResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("produk " + productId + " tidak ditemukan di catalog");
        } catch (Exception e) {
            throw new RuntimeException("catalog service tidak dapat dihubungi");
        }
    }

//    kurangi kembalikan stock di catalog service
    public void updateStock(Long productId, int stock) {
        try {
            restClient.patch()
                    .uri("/api/products/{id}/stock", productId)
                    .body(Map.of("stock", stock))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest e) {
            throw new RuntimeException("stok " + productId + " tidak cukup");
        } catch (Exception e) {
            throw new RuntimeException("catalog service tidak dapat dihubungi");
        }
    }
}
