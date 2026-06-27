package com.commerce_campus.catalog_service.controller;

import com.commerce_campus.catalog_service.dto.CatalogRequest;
import com.commerce_campus.catalog_service.dto.CatalogResponse;
import com.commerce_campus.catalog_service.service.CatalogService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService){
        this.catalogService = catalogService;
    }
//    post produk baru -  /api/products
    @PostMapping
    public ResponseEntity<CatalogResponse> create(@Valid @RequestBody CatalogRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        catalogService.create(
                                request
                        )
                );

    }
//    get semua prduk -  /api/products
    @GetMapping
    public ResponseEntity<List<CatalogResponse>> findAll(){

        return ResponseEntity.ok(
                catalogService.findAll()
        );
    }

//    get detail produk - /api/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CatalogResponse> findById(@PathVariable Long id){

        return ResponseEntity.ok(
                catalogService.findById(id)
        );
    }

//    patch update stock -/api/products/{id}/stock
    @PatchMapping("/{id}/stock")
    public ResponseEntity<CatalogResponse> updateStock(@PathVariable Long id, @RequestBody Map<String, Integer> body){
        Integer stock = body.get("stock");
        if(stock == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(catalogService.updateStock(id, stock));
    }

//    patch ubah status - /api/products/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<CatalogResponse> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body){
        String status = body.get("status");
        if(status == null || status.isBlank()){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(catalogService.updateStatus(id, status));
    }
}