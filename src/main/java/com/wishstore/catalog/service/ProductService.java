package com.wishstore.catalog.service;

import com.wishstore.catalog.dto.request.ProductRequest;
import com.wishstore.catalog.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> findAll();

    ProductResponse findById(Long id);

    ProductResponse create(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);

    List<ProductResponse> findByCategory(String category);

    List<ProductResponse> searchByName(String name);

    ProductResponse updateStock(Long id, Integer stock);

}