package com.wishstore.catalog.config;

import com.wishstore.catalog.entity.Product;
import com.wishstore.catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Carga productos de ejemplo al arrancar la aplicacion, unicamente
 * si la tabla de productos esta vacia. Simula el catalogo de Carvajal
 * sin necesidad de que un cliente cree productos manualmente.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        List<Product> products = List.of(
                Product.builder()
                        .name("Audífonos Bluetooth")
                        .description("Audífonos inalámbricos con cancelación de ruido")
                        .price(new BigDecimal("149.99"))
                        .stock(20)
                        .category("electronica")
                        .createdAt(now)
                        .updatedAt(now)
                        .build(),
                Product.builder()
                        .name("Mouse Inalámbrico")
                        .description("Mouse ergonómico con conexión Bluetooth")
                        .price(new BigDecimal("39.90"))
                        .stock(0)
                        .category("electronica")
                        .createdAt(now)
                        .updatedAt(now)
                        .build(),
                Product.builder()
                        .name("Cuaderno Profesional")
                        .description("Cuaderno de 100 hojas cuadriculado")
                        .price(new BigDecimal("5.50"))
                        .stock(150)
                        .category("papeleria")
                        .createdAt(now)
                        .updatedAt(now)
                        .build(),
                Product.builder()
                        .name("Set de Marcadores")
                        .description("Set de 12 marcadores de colores")
                        .price(new BigDecimal("12.75"))
                        .stock(8)
                        .category("papeleria")
                        .createdAt(now)
                        .updatedAt(now)
                        .build(),
                Product.builder()
                        .name("Silla Ergonómica")
                        .description("Silla de oficina con soporte lumbar")
                        .price(new BigDecimal("289.00"))
                        .stock(0)
                        .category("mobiliario")
                        .createdAt(now)
                        .updatedAt(now)
                        .build(),
                Product.builder()
                        .name("Lámpara de Escritorio LED")
                        .description("Lámpara regulable con luz cálida y fría")
                        .price(new BigDecimal("24.99"))
                        .stock(35)
                        .category("mobiliario")
                        .createdAt(now)
                        .updatedAt(now)
                        .build()
        );

        productRepository.saveAll(products);
    }
}
