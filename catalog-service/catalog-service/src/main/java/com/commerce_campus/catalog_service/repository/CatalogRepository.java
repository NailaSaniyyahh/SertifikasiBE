package com.commerce_campus.catalog_service.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.commerce_campus.catalog_service.model.Catalog;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long>{
    Optional<Catalog> findBySku(String sku);

    boolean existsBySku(String sku);
}