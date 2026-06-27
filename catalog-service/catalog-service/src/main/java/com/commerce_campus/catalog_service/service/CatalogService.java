package com.commerce_campus.catalog_service.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.commerce_campus.catalog_service.dto.CatalogRequest;
import com.commerce_campus.catalog_service.dto.CatalogResponse;
import com.commerce_campus.catalog_service.repository.CatalogRepository;
import com.commerce_campus.catalog_service.model.Catalog;
import com.commerce_campus.catalog_service.model.CatalogStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository){
        this.catalogRepository = catalogRepository;
    }

    private Catalog findCatalog(Long id){
        return catalogRepository.findById(id).orElseThrow(() -> new RuntimeException("produk id tidak ditemukan"));
    }

    //     Buat produk baru
    public CatalogResponse create(CatalogRequest catalogRequest){
//        sku unik
        if(catalogRepository.existsBySku(catalogRequest.getSku())){
            throw new RuntimeException("SKU sudah digunakan");
        }
        Catalog catalog = new Catalog();
        catalog.setSku(catalogRequest.getSku().trim().toUpperCase());
        catalog.setName(catalogRequest.getName().trim());
        catalog.setPrice(catalogRequest.getPrice());
        catalog.setStock(catalogRequest.getStock()  != null ? catalogRequest.getStock() : 0);

        Catalog saved = catalogRepository.save(catalog);
        return CatalogResponse.from(saved);
    }

    //    daftar semua produk
    @Transactional(readOnly = true)
    public List<CatalogResponse> findAll(){
        return catalogRepository.findAll().stream().map(CatalogResponse::from).toList();
    }

    //    detail produk
    @Transactional(readOnly = true)
    public CatalogResponse findById(Long id){
        return CatalogResponse.from(findCatalog(id));
    }

    //    update stock
    public CatalogResponse updateStock(Long id, int stock){
        Catalog catalog = findCatalog(id);
        int newStock = catalog.getStock() + stock;
        if (newStock < 0) {
            throw new RuntimeException("Stock tidak cukup");
        }

        catalog.setStock(newStock);
        return CatalogResponse.from(catalogRepository.save(catalog));
    }

    //    ubah status
    public CatalogResponse updateStatus(Long id, String status){
        Catalog catalog = findCatalog(id);
        CatalogStatus newStatus;
        try{
            newStatus = CatalogStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status tidak valid. Nilai yang diperbolehkan: ACTIVE, INACTIVE");
        }

        catalog.setStatus(newStatus);
        return CatalogResponse.from(catalogRepository.save(catalog));
    }
}
