-- ==============================================
-- Script de base de datos: wishstore_catalog_db
-- Microservicio: wishstore-catalog-service
-- ==============================================

-- 1. Creación de la base de datos
CREATE DATABASE IF NOT EXISTS wishstore_catalog_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- 2. Selección de la base de datos
USE wishstore_catalog_db;

-- 3. Creación de la tabla product
CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    category VARCHAR(80) NOT NULL,
    image VARCHAR(255) NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

-- ==============================================
-- Datos de prueba (opcional, útiles para probar
-- el microservicio con Postman desde el Avance 4)
-- ==============================================

INSERT INTO product (name, description, price, stock, category, image, created_at, updated_at)
VALUES
    ('Camiseta básica', 'Camiseta básica de algodón, ideal para uso diario', 45000.00, 20, 'Ropa', 'https://ejemplo.com/camiseta.jpg', NOW(), NOW()),
    ('Pantalón jean clásico', 'Pantalón jean de corte recto color azul', 89900.00, 15, 'Ropa', 'https://ejemplo.com/jean.jpg', NOW(), NOW()),
    ('Zapatillas urbanas', 'Zapatillas deportivas para uso urbano', 150000.00, 10, 'Calzado', 'https://ejemplo.com/zapatillas.jpg', NOW(), NOW()),
    ('Gorra deportiva', 'Gorra ajustable de tela impermeable', 25000.00, 30, 'Accesorios', NULL, NOW(), NOW()),
    ('Mochila urbana', 'Mochila resistente al agua con compartimento para laptop', 120000.00, 8, 'Accesorios', 'https://ejemplo.com/mochila.jpg', NOW(), NOW());