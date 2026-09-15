# wishstore-catalog-service

## 1. Nombre del proyecto

**wishstore-catalog-service** — Microservicio de catálogo de productos para WishStore.

## 2. Objetivo

Administrar el catálogo de productos de WishStore: crear, consultar, actualizar, eliminar, buscar productos y gestionar su disponibilidad mediante el control de stock. Es un microservicio completamente independiente, con su propia base de datos.

## 3. Tecnologías

- Java 21
- Spring Boot 3.x
- Maven
- MySQL
- Spring Data JPA
- Spring Validation
- Lombok
- Spring Web
- Spring Boot DevTools
- Docker

## 4. Arquitectura

```text
Cliente
  ↓
ProductController
  ↓
ProductService
  ↓
ProductServiceImpl
  ↓
ProductRepository
  ↓
MySQL
```

`ProductMapper` se encarga de las conversiones Entity ↔ DTO. Toda la lógica de negocio vive en `ProductServiceImpl`; el Controller solo recibe peticiones y delega.

## 5. Estructura de carpetas

```text
wishstore-catalog-service/
├── src/main/java/com/wishstore/catalog/
│   ├── controller/
│   ├── service/
│   │   └── impl/
│   ├── repository/
│   ├── entity/
│   ├── dto/
│   │   ├── request/
│   │   └── response/
│   ├── mapper/
│   ├── exception/
│   └── CatalogApplication.java
├── src/main/resources/application.yml
├── Dockerfile
├── pom.xml
├── wishstore_catalog_db.sql
└── postman/
```

## 6. Requisitos previos

- Java 21 instalado (JDK).
- Maven (o usar el wrapper `./mvnw` incluido en el proyecto).
- MySQL 8.x corriendo localmente o accesible por red.
- Docker (opcional, para ejecutar en contenedor).

## 7. Configuración de MySQL

Ejecutar el script `wishstore_catalog_db.sql` en tu servidor MySQL:

```bash
mysql -u root -p < wishstore_catalog_db.sql
```

Esto crea la base de datos `wishstore_catalog_db`, la tabla `product` y algunos datos de prueba.

## 8. Variables de entorno

El proyecto no contiene credenciales reales; se configuran mediante variables de entorno:

| Variable      | Descripción                  | Valor por defecto        |
|---------------|-------------------------------|---------------------------|
| `DB_HOST`     | Host de MySQL                 | `localhost`               |
| `DB_PORT`     | Puerto de MySQL                | `3306`                    |
| `DB_NAME`     | Nombre de la base de datos     | `wishstore_catalog_db`    |
| `DB_USERNAME` | Usuario de MySQL                | `root`                    |
| `DB_PASSWORD` | Contraseña de MySQL             | `root`                    |

Ejemplo para exportarlas en Linux/Mac:

```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=wishstore_catalog_db
export DB_USERNAME=root
export DB_PASSWORD=mi_password_seguro
```

## 9. Ejecución local

```bash
./mvnw spring-boot:run
```

La aplicación quedará disponible en:

```text
http://localhost:8081
```

## 10. Compilación Maven

```bash
./mvnw clean package
```

El JAR generado quedará en:

```text
target/wishstore-catalog-service.jar
```

Se puede ejecutar directamente con:

```bash
java -jar target/wishstore-catalog-service.jar
```

## 11. Pruebas

```bash
./mvnw clean test
```

También se recomienda probar todos los endpoints con la colección de Postman incluida (ver sección 16).

## 12. Docker

### Construir la imagen

```bash
docker build -t wishstore-catalog-service .
```

### Ejecutar el contenedor

```bash
docker run -p 8081:8081 \
  -e DB_HOST=host.docker.internal \
  -e DB_PORT=3306 \
  -e DB_NAME=wishstore_catalog_db \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=mi_password_seguro \
  wishstore-catalog-service
```

> `host.docker.internal` permite que el contenedor acceda a un MySQL corriendo en tu máquina local (funciona en Docker Desktop / Mac / Windows; en Linux puede requerir `--add-host=host.docker.internal:host-gateway`).

Este proyecto **no** incluye `docker-compose.yml`, ya que está fuera del alcance definido.

## 13. Endpoints

| Método | Endpoint                        | Descripción            |
|--------|----------------------------------|--------------------------|
| GET    | `/products`                     | Listar productos         |
| GET    | `/products/{id}`                | Buscar por ID             |
| POST   | `/products`                     | Crear producto            |
| PUT    | `/products/{id}`                | Actualizar producto       |
| DELETE | `/products/{id}`                | Eliminar producto         |
| GET    | `/products/category/{category}` | Buscar por categoría      |
| GET    | `/products/search?name=`        | Buscar por nombre          |
| PATCH  | `/products/{id}/stock`          | Actualizar stock            |

## 14. Ejemplos de requests

### Crear producto

```http
POST /products
Content-Type: application/json

{
  "name": "Camiseta básica",
  "description": "Camiseta básica de algodón",
  "price": 45000.00,
  "stock": 20,
  "category": "Ropa",
  "image": "https://ejemplo.com/camiseta.jpg"
}
```

### Actualizar stock

```http
PATCH /products/1/stock
Content-Type: application/json

{
  "stock": 50
}
```

## 15. Ejemplos de responses

### Respuesta exitosa (201 Created)

```json
{
  "id": 6,
  "name": "Camiseta básica",
  "description": "Camiseta básica de algodón",
  "price": 45000.00,
  "stock": 20,
  "category": "Ropa",
  "image": "https://ejemplo.com/camiseta.jpg"
}
```

### Producto no encontrado (404 Not Found)

```json
{
  "timestamp": "2026-09-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "No se encontró un producto con el id: 99999"
}
```

### Producto duplicado (409 Conflict)

```json
{
  "timestamp": "2026-09-15T10:31:00",
  "status": 409,
  "error": "Conflict",
  "message": "Ya existe un producto registrado con el nombre: Camiseta básica"
}
```

### Error de validación (400 Bad Request)

```json
{
  "timestamp": "2026-09-15T10:32:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Error de validación en los datos enviados",
  "errors": {
    "name": "El nombre del producto es obligatorio",
    "price": "El precio debe ser mayor que cero"
  }
}
```

## 16. Importación de Postman

1. Abrir Postman.
2. Click en **Import**.
3. Seleccionar el archivo `postman/wishstore-catalog-service.postman_collection.json`.
4. Ajustar la variable `baseUrl` si el servicio no corre en `http://localhost:8081`.
5. Ejecutar las peticiones incluidas: CRUD completo, duplicados, validaciones, búsquedas y actualización de stock.

## 17. Preparación para despliegue

- El servicio expone el puerto `8081` y es completamente independiente (no depende de otros microservicios).
- Las credenciales de MySQL se inyectan por variables de entorno, nunca están hardcodeadas en el repositorio.
- El `Dockerfile` genera una imagen lista para ejecutar con `docker run`.
- No se incluye `docker-compose.yml` ni configuración de una plataforma de despliegue específica, ya que están fuera del alcance de este proyecto.