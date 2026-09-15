package com.wishstore.catalog.service.impl;

import com.wishstore.catalog.dto.request.ProductRequest;
import com.wishstore.catalog.dto.response.ProductResponse;
import com.wishstore.catalog.entity.Product;
import com.wishstore.catalog.exception.DuplicateProductException;
import com.wishstore.catalog.exception.ResourceNotFoundException;
import com.wishstore.catalog.mapper.ProductMapper;
import com.wishstore.catalog.repository.ProductRepository;
import com.wishstore.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return productMapper.toResponseList(products);
    }

    @Override
    public ProductResponse findById(Long id) {
        Product product = findProductOrThrow(id);
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new DuplicateProductException(
                    "Ya existe un producto registrado con el nombre: " + request.getName());
        }

        Product product = productMapper.toEntity(request);
        LocalDateTime now = LocalDateTime.now();
        product.setCreatedAt(now);
        product.setUpdatedAt(now);

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = findProductOrThrow(id);

        productMapper.updateEntity(product, request);
        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public void delete(Long id) {
        Product product = findProductOrThrow(id);
        productRepository.delete(product);
    }

    @Override
    public List<ProductResponse> findByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        return productMapper.toResponseList(products);
    }

    @Override
    public List<ProductResponse> searchByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return productMapper.toResponseList(products);
    }

    @Override
    public ProductResponse updateStock(Long id, Integer stock) {
        Product product = findProductOrThrow(id);

        product.setStock(stock);
        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponse(updatedProduct);
    }

    private Product findProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró un producto con el id: " + id));
    }

}